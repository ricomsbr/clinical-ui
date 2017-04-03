package br.com.ackta.clinical.business.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PatientRepository;

@Service
@Transactional
public class PatientService implements IPatientService {
	private PatientRepository patientRepository;
	private PersonalDataService personalDataService;

	public PatientService(PatientRepository patientRepository1,
			PersonalDataService personalDataService1) {
		super();
		this.patientRepository = patientRepository1;
		this.personalDataService = personalDataService1;
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
	public Patient insert(Patient patient) {
		PersonalData personalData = personalDataService.insert(patient.getPersonalData());
		patient.setPersonalData(personalDate);
		Patient result = patientRepository.save(patient);
		return result;
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

}
