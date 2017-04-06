package br.com.ackta.clinical.business.helper;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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
import br.com.ackta.clinical.business.service.IPhoneService;
import br.com.ackta.clinical.business.service.PersonalDataService;
import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.AddressType;
import br.com.ackta.clinical.data.entity.Convenant;
import br.com.ackta.clinical.data.entity.ConvenantMember;
import br.com.ackta.clinical.data.entity.FamilyMember;
import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.Kinship;
import br.com.ackta.clinical.data.entity.MedicalHistory;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.entity.Phone;
import br.com.ackta.clinical.data.entity.PhoneType;
import br.com.ackta.clinical.presentation.Form;

@Service
@Transactional
public class PatientHelper implements IPatientHelper {
	private static final Integer COUNTRY_CODE = 55;
	private IPatientService patientService;
	private IConvenantMemberService convenantMemberService;
	private IAddressService addressService;
	private IPhoneService phoneService;
	private PersonalDataService personalDataService;

	/**
	 * @param service
	 * @param addressService1
	 * @param phoneService1
	 * @param addressRepository1
	 */
	public PatientHelper(IPatientService service, IConvenantMemberService convenantMemberService1,
			IAddressService addressService1, IPhoneService phoneService1,
			PersonalDataService personalDataService1) {
		super();
		this.patientService = service;
		this.convenantMemberService = convenantMemberService1;
		this.addressService = addressService1;
		this.phoneService = phoneService1;
		this.personalDataService = personalDataService1;
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

	private List<PersonalData> addResponsibles(Form form) {
		List<PersonalData> result = new ArrayList<>();
		Optional<PersonalData> responsible1 = createResponsible(form, form.getResponsibleName1(), form.getResponsiblePhone1());
		Optional<PersonalData> responsible2 = createResponsible(form, form.getResponsibleName2(), form.getResponsiblePhone2());
		if (responsible1.isPresent()) {
			result.add(personalDataService.save(responsible1.get()));
		}
		if (responsible2.isPresent()) {
			result.add(personalDataService.save(responsible2.get()));
		}
		return result;
	}

	private Optional<PersonalData> createResponsible(Form form, String respName, String respPhone) {
		PersonalData result = null;
		if (!respName.isEmpty()) {
			result = new PersonalData();
			result.setName(respName);
			if (!respPhone.isEmpty()) {
				Phone phone = new Phone(1, PhoneType.GENERAL, COUNTRY_CODE, respPhone, result);
				result.addPhone(phoneService.insert(phone));
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
	public IPatient insert(Form form) {
		PersonalData data = new PersonalData();
		BeanUtils.copyProperties(form, data);
		List<PersonalData> responsibles = addResponsibles(form);
		PersonalData personalData = personalDataService.validateAndSave(data);
		addAddress(form, data);
		addPhones(form, data);
		Patient patient = new Patient(personalData, extractConvenantMembers(form), responsibles, form.getObservation());
		MedicalHistory medicalHistory = new MedicalHistory(form.getMedicalHistory());
		medicalHistory.setFamilyMembers(extractFamilyMembers(form));
		patient.setMedicalHistory(medicalHistory);
		IPatient result = patientService.validateAndSave(patient);
		return result;
	}

	@Override
	public Page<Patient> search(Form form, Pageable pageable) {
		PersonalData data = new PersonalData();

		String cpf = form.getCpf();
		if(!cpf.isEmpty()) {
			data.setCpf(cpf);
		}
		String name = form.getName();
		if (!name.isEmpty()) {
			data.setName(name);
		}
		LocalDate birthDate = form.getBirthDate();
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
		updateAddress(form, data);
		updatePhones(form, data);
		patient.setConvenantMembers(extractConvenantMembers(form));
		updateResponsibles(patient, form);
		patient.setObservation(form.getObservation());
		MedicalHistory medicalHistory = new MedicalHistory(form.getMedicalHistory());
		medicalHistory.setFamilyMembers(extractFamilyMembers(form));
		patient.setMedicalHistory(medicalHistory);
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

	private List<PersonalData> updateResponsibles(Patient patient, Form form) {
		List<PersonalData> responsibles = patient.getResponsibles();
		PersonalData resp1 = responsibles.get(0);
		PersonalData resp2 = responsibles.get(1);
		resp1.setName(form.getResponsibleName1());
		resp1.getPhones().first().setNumber(form.getResponsiblePhone1());
		resp2.setName(form.getResponsibleName2());
		resp2.getPhones().first().setNumber(form.getResponsiblePhone2());
		personalDataService.validateName(resp1);
		personalDataService.save(resp1);
		personalDataService.validateName(resp2);
		personalDataService.save(resp2);

		return responsibles;
	}
}
