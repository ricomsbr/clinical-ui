package br.com.ackta.clinical.business.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.SerializableResourceBundleMessageSource;
import br.com.ackta.clinical.WebConfig;
import br.com.ackta.clinical.business.service.validator.NotDuplicatePatientCpfValidator;
import br.com.ackta.clinical.business.service.validator.NotDuplicatePatientMailValidator;
import br.com.ackta.clinical.business.service.validator.NotDuplicatePatientNameValidator;
import br.com.ackta.clinical.business.service.validator.PatientValidator;
import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.FamilyMember;
import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.data.entity.MedicalHistory;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.entity.Phone;
import br.com.ackta.clinical.data.entity.Responsible;
import br.com.ackta.clinical.data.repository.PatientRepository;
import br.com.ackta.util.EnumUtil;
import br.com.ackta.util.ReportUtil;
import br.com.ackta.validation.MaxValidator;
import br.com.ackta.validation.MinValidator;
import br.com.ackta.validation.NotNullValidator;
import br.com.ackta.validation.ValidatorServiceBuilder;

@Service
@Transactional
public class PatientService implements IPatientService {
	private PatientRepository patientRepository;
	private PersonalDataService personalDataService;
	private WebConfig reportConfig;
	private SerializableResourceBundleMessageSource messageSource;

	public PatientService(PatientRepository patientRepository1,
			PersonalDataService personalDataService1, 
			WebConfig reportConfig1, 
			SerializableResourceBundleMessageSource messageSource1) {
		super();
		this.patientRepository = patientRepository1;
		this.personalDataService = personalDataService1;
		this.reportConfig = reportConfig1;
		this.messageSource = messageSource1;
	}

	@Override
	public void delete(Long id) {
		patientRepository.delete(id);
	}

	@Override
	public Page<Patient> findAll(Example<Patient> example, Pageable pageable) {
		Page<Patient> result = patientRepository.findAll(example, pageable);
		return result;
	}

	@Override
	public Optional<Patient> findOne(Long id) {
		return Optional.of(patientRepository.findOne(id));
	}

	@Override
	public Patient validateAndSave(Patient patient) {
		validate(patient);
		Patient result = patientRepository.save(patient);
		return result;
	}
	
	@Override
	public Patient update(Patient patient) {
		IPersonalData data = patient.getPersonalData();
		Patient dbObj = patientRepository.findOne(patient.getId());
		PersonalData dbData = dbObj.getPersonalData();
		dbData.merge(data);
		Patient result = validateAndSave(dbObj);
		return result;
	}
	
	@Override
	public void validate(Patient patient) {
		ValidatorServiceBuilder
			.build(patient, patient.getClass().getName())
			.append(new PatientValidator(personalDataService))
			.append(new NotDuplicatePatientNameValidator(patientRepository))
			.append(new NotDuplicatePatientMailValidator(patientRepository))
			.append(new NotDuplicatePatientCpfValidator(patientRepository))
			.validate();
		validateMedicalHistory(patient);
	}

	private void validateMedicalHistory(Patient patient) {
		MedicalHistory history = patient.getMedicalHistory();
		ValidatorServiceBuilder
			.build(history, patient.getClass().getName())
			.append(new NotNullValidator("allergic"))
			.append(new NotNullValidator("smoker"))
			.append(new NotNullValidator("hasDiseases"))
			.append(new NotNullValidator("hasSurgeries"))
			.append(new NotNullValidator("weight"))
			.append(new MinValidator("weight", 1d))
			.append(new MaxValidator("weight", 400d))
			.append(new NotNullValidator("height"))
			.append(new MinValidator("height", 15d))
			.append(new MaxValidator("height", 260d))
			.validate();
	}

	@Override
	public void generatePdfFile(Patient patient) {
		String filePath = reportConfig.getPatientReportPath();
		Resource layout = reportConfig.getPatientReportLayout();
		Collection<Map<String, Object>> beanCollection = prepareBeanCollection(patient);
		ReportUtil.createAndSavePdf(filePath, layout, beanCollection);
	}

	
	@Override
	public byte[] generatePdf(Patient patient) {
		Resource layout = reportConfig.getPatientReportLayout();
		Collection<Map<String, Object>> beanCollection = prepareBeanCollection(patient);
		return ReportUtil.createPdf(layout, beanCollection);
	}
	
	/**
	 * @param patient
	 * @return
	 */
	private Collection<Map<String, Object>> prepareBeanCollection(Patient patient) {
		Map<String, Object> map = new HashMap<>();
		Locale locale = LocaleContextHolder.getLocale();
		PersonalData personalData = patient.getPersonalData();
		map.put("name", personalData.getName());
		map.put("childrenQty", personalData.getChildrenQty());
		map.put("cpf", personalData.getCpf());
		String gender = EnumUtil.getKey(personalData.getGender());
		map.put("gender", messageSource.getMessage(gender, null, gender, locale));
		map.put("birthDate", Date.valueOf(personalData.getBirthDate()));
		Address address = personalData.getAddresses().first();
		map.put("publicArea", address.getPublicArea());
		map.put("city", address.getCity());
		map.put("complement", address.getComplement());
		map.put("district", address.getDistrict());
		map.put("number", address.getNumber());
		map.put("zipCode", address.getZipCode());
		Collection<Phone> phones = personalData.getPhones();
		map.put("phones", phones);
		map.put("mail", personalData.getMail());
		map.put("maritalState", personalData.getMaritalState());
		map.put("profession", personalData.getProfession());
		map.put("rg", personalData.getRg());
		map.put("convenantMembers", patient.getConvenantMembers());
		MedicalHistory medicalHistory = patient.getMedicalHistory();
		map.put("weight", medicalHistory.getWeight());
		map.put("height", medicalHistory.getHeight());
		map.put("smoker", medicalHistory.getSmoker());
		map.put("smokeFrequence", medicalHistory.getSmokeFrequence());
		String smokePeriodicityUnit = "";
		if (medicalHistory.getSmokePeriodicityUnit() != null) {
			smokePeriodicityUnit = EnumUtil.getKey(medicalHistory.getSmokePeriodicityUnit());
		}
		map.put("smokePeriodicityUnit", messageSource.getMessage(smokePeriodicityUnit, null, smokePeriodicityUnit, locale));
		map.put("drinker", medicalHistory.getDrinker());
		map.put("drinkFrequence", medicalHistory.getDrinkFrequence());
		String drinkPeriodicityUnit = "";
		if (medicalHistory.getDrinkPeriodicityUnit() != null) {
			drinkPeriodicityUnit = EnumUtil.getKey(medicalHistory.getDrinkPeriodicityUnit());
		}
		map.put("drinkPeriodicityUnit", messageSource.getMessage(drinkPeriodicityUnit, null, drinkPeriodicityUnit, locale));
		map.put("allergic", medicalHistory.getAllergic());
		map.put("allergies", medicalHistory.getAllergies());
		map.put("hasDiseases", medicalHistory.getHasDiseases());
		map.put("diseases", medicalHistory.getDiseases());
		map.put("hasSurgeries", medicalHistory.getHasSurgeries());
		map.put("surgeries", medicalHistory.getSurgeries());
		Collection<FamilyMember> familyMembers = medicalHistory.getFamilyMembers();
		map.put("familyMembers", familyMembers);
		Collection<Responsible> responsibles = patient.getResponsibles();
		map.put("responsibles", responsibles);
		map.put("observation", patient.getObservation());
		Collection<Map<String, Object>> beanCollection = new ArrayList<>();
		beanCollection.add(map);
		return beanCollection;
	}
}
