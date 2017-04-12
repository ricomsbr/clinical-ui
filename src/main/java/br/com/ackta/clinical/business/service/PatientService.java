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
import br.com.ackta.clinical.business.service.validator.PatientValidator;
import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.data.entity.MedicalHistory;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PatientRepository;
import br.com.ackta.util.EnumUtil;
import br.com.ackta.util.ReportUtil;
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
	public void validate(Patient patient) {
		ValidatorServiceBuilder
			.build(patient, patient.getClass().getName())
			.append(new PatientValidator(personalDataService))
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
			.append(new NotNullValidator("height"))
			.validate();
	}
	
	@Override
	public Patient update(Patient patient) {
		IPersonalData data = patient.getPersonalData();
		Patient dbObj = patientRepository.findOne(patient.getId());
		PersonalData dbData = dbObj.getPersonalData();
		dbData.merge(data);
		Patient result = patientRepository.save(dbObj);
		return result;
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
		PersonalData personalData = patient.getPersonalData();
		map.put("name", personalData.getName());
		map.put("childrenQty", personalData.getChildrenQty());
		map.put("cpf", personalData.getCpf());
		Gender gender = personalData.getGender();
		String code = EnumUtil.getKey(gender);
		Locale locale = LocaleContextHolder.getLocale();
		map.put("gender", messageSource.getMessage(code, null, code, locale));
		map.put("birthDate", Date.valueOf(personalData.getBirthDate()));
		map.put("observation", patient.getObservation());
		Address address = personalData.getAddresses().first();
		map.put("publicArea", address.getPublicArea());
		map.put("city", address.getCity());
		map.put("complement", address.getComplement());
		map.put("district", address.getDistrict());
		map.put("number", address.getNumber());
		map.put("zipCode", address.getZipCode());
		Collection<Map<String, Object>> beanCollection = new ArrayList<>();
		beanCollection.add(map);
		return beanCollection;
	}
}
