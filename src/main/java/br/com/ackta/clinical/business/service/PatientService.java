package br.com.ackta.clinical.business.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ackta.clinical.model.entity.Gender;
import br.com.ackta.clinical.model.entity.IPersonalData;
import br.com.ackta.clinical.model.entity.Patient;
import br.com.ackta.clinical.model.entity.PersonalData;
import br.com.ackta.clinical.model.repository.PatientRepository;

@Service
public class PatientService implements IPatientService {
	PatientRepository patientRepository;

	/**
	 * @param patientRepository
	 */
	public PatientService(PatientRepository patientRepository) {
		super();
		this.patientRepository = patientRepository;
	}

	@Override
	public void delete(Long id) {
		patientRepository.delete(id);
	}

	@Override
	public Page<Patient> findAll(Example<Patient> example, Pageable pageable) {
		@SuppressWarnings("unchecked")
		Page<Patient> result = (Page<Patient>)(Page<?>) patientRepository.findAll(example, pageable);
		return result;
	}

	@Override
	public Optional<Patient> findOne(Long id) {
		return Optional.of(patientRepository.findOne(id));
	}

	@Override
	public Patient insert(Patient patient) {
		if (Objects.nonNull(patient.getId())) {
			throw new ServiceException("Id should be null.");
		}
		Patient result = patientRepository.save((Patient) patient);
		return result;
	}

	@Override
	public Patient update(Patient patient) {
		IPersonalData data = patient.getPersonalData();
		Patient dbObj = patientRepository.findOne(patient.getId());
		PersonalData dbData = (PersonalData) dbObj.getPersonalData();

		dbData.setBirthDate(data.getBirthDate());
		dbData.setCpf(data.getCpf());
		dbData.setGender(Gender.MALE);
		dbData.setName(data.getName());
		dbData.setRg(data.getRg());
		Patient result = patientRepository.save(dbObj);
		return result;
	}


}
