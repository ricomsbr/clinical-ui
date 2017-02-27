package br.com.ackta.clinical.business.service;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;

import org.assertj.core.util.Lists;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.IAddress;
import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PatientRepository;

@Service
@Transactional
public class PatientService implements IPatientService {
	private PatientRepository patientRepository;

	/**
	 * @param patientRepository
	 */
	public PatientService(PatientRepository patientRepository1) {
		super();
		this.patientRepository = patientRepository1;
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
		validateInsert(patient);
		Patient result = patientRepository.save(patient);
		return result;
	}

	@Override
	public Patient update(Patient patient) {
		IPersonalData data = patient.getPersonalData();
		Patient dbObj = patientRepository.findOne(patient.getId());
		PersonalData dbData = (PersonalData) dbObj.getPersonalData();
		dbData.merge(data);
		Patient result = patientRepository.save(dbObj);
		return result;
	}

	private void validateInsert(Patient patient) {
		IPersonalData personalData = patient.getPersonalData();
		if (Objects.nonNull(personalData.getId())) {
			throw new RuntimeException("Id should be null");
		}
		Objects.requireNonNull(personalData.getBirthDate(), "BirthDate should not be null");
		Integer childrenQty = personalData.getChildrenQty();
		Objects.requireNonNull(childrenQty, "ChildrenQty should not be null");
		if (childrenQty < 0) {
			throw new RuntimeException("ChildrenQty should be a nonNegative value");
		}
	}
}
