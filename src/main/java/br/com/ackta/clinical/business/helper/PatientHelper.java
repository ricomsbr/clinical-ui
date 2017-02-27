package br.com.ackta.clinical.business.helper;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.business.service.IPatientService;
import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.AddressType;
import br.com.ackta.clinical.data.entity.IPatient;
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

	/**
	 * @param service
	 * @param addressRepository1
	 */
	public PatientHelper(IPatientService service) {
		super();
		this.patientService = service;
	}

	private void addAddress(Form form, PersonalData data) {
		Address address = new Address(1, AddressType.HOME);
		BeanUtils.copyProperties(form, address);
		data.getAddresses().add(address);
	}

	private void addPhones(Form form, PersonalData data) {
		Phone mobile = new Phone(1, PhoneType.MOBILE, COUNTRY_CODE, form.getMobileRegionalCode(), form.getMobilePhone());
		Phone homePhone = new Phone(2, PhoneType.HOME, COUNTRY_CODE, form.getHomeRegionalCode(), form.getHomePhone());
		data.getPhones().add(homePhone);
		data.getPhones().add(mobile);
	}

	@Override
	public void delete(Long id) {
		patientService.delete(id);
	}

	private Page<Patient> findAll(Example<Patient> example, Pageable pageable) {
		Page<Patient> result = patientService.findAll(example, pageable);
		return result;
	}

	@Override
	public Patient findOne(Long id) {
		Optional<Patient> result = patientService.findOne(id);
		return result.get();
	}

	@Override
	public IPatient insert(Form form) {
		PersonalData data = new PersonalData();
		BeanUtils.copyProperties(form, data);
		addAddress(form, data);
		addPhones(form, data);
		Patient patient = new Patient(data);
		IPatient result = patientService.insert(patient);
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
		PersonalData data = new PersonalData();
		BeanUtils.copyProperties(form, data);

		Patient patient = new Patient(data);
		patient.setId(id);
		addAddress(form, data);
		addPhones(form, data);
		Patient result = patientService.update(patient);
		return result;
	}
}
