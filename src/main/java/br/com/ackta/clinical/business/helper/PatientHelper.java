package br.com.ackta.clinical.business.helper;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.business.service.IAddressService;
import br.com.ackta.clinical.business.service.IConvenantMemberService;
import br.com.ackta.clinical.business.service.IPatientService;
import br.com.ackta.clinical.business.service.IPersonalDataService;
import br.com.ackta.clinical.business.service.IPhoneService;
import br.com.ackta.clinical.business.service.IResponsibleService;
import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.AddressType;
import br.com.ackta.clinical.data.entity.Convenant;
import br.com.ackta.clinical.data.entity.ConvenantMember;
import br.com.ackta.clinical.data.entity.FamilyMember;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.Kinship;
import br.com.ackta.clinical.data.entity.MedicalHistory;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.entity.Phone;
import br.com.ackta.clinical.data.entity.PhoneType;
import br.com.ackta.clinical.data.entity.Responsible;
import br.com.ackta.clinical.presentation.Form;

@Service
@Transactional
public class PatientHelper implements IPatientHelper {
	private static final Integer COUNTRY_CODE = 55;
	private IPatientService patientService;
	private IConvenantMemberService convenantMemberService;
	private IAddressService addressService;
	private IPhoneService phoneService;
	private IPersonalDataService personalDataService;
	private IResponsibleService responsibleService;


	@Autowired
	public PatientHelper(IPatientService service, IConvenantMemberService convenantMemberService1,
			IAddressService addressService1, IPhoneService phoneService1,
			IPersonalDataService personalDataService1, IResponsibleService responsibleService1) {
		super();
		this.patientService = service;
		this.convenantMemberService = convenantMemberService1;
		this.addressService = addressService1;
		this.phoneService = phoneService1;
		this.personalDataService = personalDataService1;
		this.responsibleService = responsibleService1;
	}

	private void addAddress(Form form, PersonalData data) {
		Address address = new Address(1, AddressType.HOME, data);
		BeanUtils.copyProperties(form, address);
		data.getAddresses().add(addressService.insert(address));
	}

	private void addPhones(Form form, PersonalData data) {
		Phone mobile = new Phone(1, PhoneType.MOBILE, COUNTRY_CODE, form.getMobilePhone(), data);
		Phone homePhone = new Phone(2, PhoneType.HOME, COUNTRY_CODE, form.getHomePhone(), data);
		data.addPhone(phoneService.insert(homePhone));
		data.addPhone(phoneService.insert(mobile));
	}

	private List<Responsible> addResponsibles(Form form) {
		List<Responsible> result = new ArrayList<>();
		Optional<Responsible> responsible1 = createResponsible(form.getResponsibleName1(), form.getResponsiblePhone1());
		Optional<Responsible> responsible2 = createResponsible(form.getResponsibleName2(), form.getResponsiblePhone2());
		if (responsible1.isPresent()) {
			Responsible resp1 = responsible1.get();
			result.add(resp1);
		}
		if (responsible2.isPresent()) {
			Responsible resp2 = responsible2.get();
			result.add(resp2);
		}
		return result;
	}

	private Optional<Responsible> createResponsible(String respName, String respPhone) {
		Responsible result = null;
		if (!respName.isEmpty()) {
			result = new Responsible();
			result.setName(respName);
//			result = responsibleService.save(result);
			if (!respPhone.isEmpty()) {
				result.setPhoneNumber(respPhone);
			}
		}
		return Optional.ofNullable(result);
	}

	@Override
	public void delete(Long id) {
		patientService.delete(id);
	}

	private List<ConvenantMember> extractConvenantMembers(Form form) {
		List<ConvenantMember> result = new ArrayList<>();
		if (form.getIsMember()) {
			ConvenantMember ownMember = new ConvenantMember(Convenant.OWN);
			result.add(ownMember);
		}
		String susCard = form.getSusCard();
		if (!susCard.isEmpty()) {
			ConvenantMember susMember = new ConvenantMember(Convenant.SUS);
			susMember.setCardNumber(susCard);
			result.add(susMember);
		}
		return result;
	}


	private List<FamilyMember> extractFamilyMembers(Form form) {
		List<FamilyMember> result = new ArrayList<>();
		FamilyMember motherHistory = new FamilyMember();
		BeanUtils.copyProperties(form.getMotherHistory(), motherHistory);
		motherHistory.setKinship(Kinship.MOTHER);
		if (Objects.nonNull(form.getMotherHistory().getAge())) {
			motherHistory.setBirthYear(Year.now().minusYears(form.getMotherHistory().getAge()));
		}
		result.add(motherHistory);
		FamilyMember fatherHistory = new FamilyMember();
		BeanUtils.copyProperties(form.getFatherHistory(), fatherHistory);
		fatherHistory.setKinship(Kinship.FATHER);
		if (Objects.nonNull(form.getFatherHistory().getAge())) {
			fatherHistory.setBirthYear(Year.now().minusYears(form.getFatherHistory().getAge()));
		}
		result.add(fatherHistory);
		return result;
	}


	private Page<Patient> findAll(Example<Patient> example, Pageable pageable) {
		Page<Patient> result = patientService.findAll(example, pageable);
		return result;
	}

	@Override
	public Form findOne(Long id) {
		Patient patient = patientService.findOne(id).get();
		Optional<ConvenantMember> susMember = convenantMemberService.findByConvenant(patient.getConvenantMembers(), Convenant.SUS);
		Optional<ConvenantMember> ownMember = convenantMemberService.findByConvenant(patient.getConvenantMembers(), Convenant.OWN);
		Boolean isOwnMember = ownMember.isPresent();
		String susCard = susMember.isPresent() ? susMember.get().getCardNumber() : null;
		Form result = new Form(patient, isOwnMember, susCard);
		return result;
	}

	@Override
	public byte[] generatePdf(Long id) {
		Patient patient = patientService.findOne(id).get();
		return patientService.generatePdf(patient);
	}

	@Override
	public IPatient insert(Form form) {
		PersonalData data = new PersonalData();
		BeanUtils.copyProperties(form, data);
		List<Responsible> responsibles = addResponsibles(form);
		PersonalData personalData = personalDataService.validateAndSave(data);
		addAddress(form, data);
		addPhones(form, data);
		Patient patient = new Patient(personalData, extractConvenantMembers(form), responsibles, form.getObservation());
		responsibles.stream().forEach(r -> r.setPatient(patient));
		MedicalHistory medicalHistory = new MedicalHistory(form.getMedicalHistory());
		medicalHistory.setFamilyMembers(extractFamilyMembers(form));
		patient.setMedicalHistory(medicalHistory);
		IPatient result = patientService.validateAndSave(patient);
		return result;
	}

	@Override
	public Page<Patient> search(String name, String cpf, String rg, Gender gender, LocalDate birthDate, Pageable pageable) {
		PersonalData data = new PersonalData();
		if (name != null && !name.isEmpty()) {
			data.setName(name);
		}
		if(cpf != null && !cpf.isEmpty()) {
			data.setCpf(cpf);
		}
		if (rg != null && !rg.isEmpty()) {
			data.setRg(rg);
		}
		if (gender != null) {
			data.setGender(gender);
		}
		if (birthDate != null) {
			data.setBirthDate(birthDate);
		}
		Patient probe = new Patient(data);
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase()
				.withMatcher("personalData.name", match -> match.stringMatcher(StringMatcher.CONTAINING));
		Example<Patient> example = Example.of(probe, matcher);

		Page<Patient> result = findAll(example, pageable);

		return result;
	}

	@Override
	public Patient update(Long id, Form form) {
		Patient patient = patientService.findOne(id).get();
		PersonalData data = patient.getPersonalData();
		BeanUtils.copyProperties(form, data, "id");
		personalDataService.validate(data);
		updateAddress(form, data);
		updatePhones(form, data);
		patient.setConvenantMembers(extractConvenantMembers(form));
		List<Responsible> responsibles = updateResponsibles(patient, form);
		responsibles.stream().forEach(r -> r.setPatient(patient));
		patient.setResponsibles(responsibles);
		patient.setObservation(form.getObservation());
		MedicalHistory medicalHistory = new MedicalHistory(form.getMedicalHistory());
		medicalHistory.setFamilyMembers(extractFamilyMembers(form));
		patient.setMedicalHistory(medicalHistory);
		patientService.validate(patient);
		Patient result = patientService.update(patient);
		return result;
	}

	private void updateAddress(Form form, PersonalData data) {
		Address address = data.getAddresses().first();
		BeanUtils.copyProperties(form, address, "id");
		data.getAddresses().add(addressService.insert(address));
	}

	private void updatePhones(Form form, PersonalData data) {
		data.getPhones().forEach(p -> {
			if(PhoneType.MOBILE.equals(p.getType())) {
				p.setNumber(form.getMobilePhone());
			} else if (PhoneType.HOME.equals(p.getType())) {
				p.setNumber(form.getHomePhone());
			}
		});
	}

	private List<Responsible> updateResponsibles(Patient patient, Form form) {
		List<Responsible> responsibles = patient.getResponsibles();
		Optional<Responsible> newResp1 = createResponsible(form.getResponsibleName1(), form.getResponsiblePhone1());
		Optional<Responsible> newResp2 = createResponsible(form.getResponsibleName2(), form.getResponsiblePhone2());
		if (responsibles.size() > 0) {
			Responsible resp1 = responsibles.get(0);
			if  (newResp1.isPresent()) {
				resp1.merge(newResp1.get());
			} else {
				resp1.setPatient(null);
				responsibles.remove(resp1);
			}
		} else if (newResp1.isPresent()) {
			responsibles.add(newResp1.get());
		}
		if (responsibles.size() > 1) {
			Responsible resp2 = responsibles.get(1);
			if  (newResp2.isPresent()) {
				resp2.merge(newResp2.get());
			} else {
				resp2.setPatient(null);
				responsibles.remove(resp2);
			}
		} else if (newResp2.isPresent()) {
			responsibles.add(newResp2.get());
		}
		return responsibles;
	}
}
