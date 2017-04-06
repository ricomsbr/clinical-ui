package br.com.ackta.clinical.business.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.business.service.validator.PatientValidator;
import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.data.entity.MedicalHistory;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PatientRepository;
import br.com.ackta.validation.IsNotNullValidator;
import br.com.ackta.validation.ValidatorServiceBuilder;

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
			.append(new IsNotNullValidator("medicalHistory.allergic"))
			.append(new IsNotNullValidator("medicalHistory.smoker"))
			.append(new IsNotNullValidator("medicalHistory.hasDiseases"))
			.append(new IsNotNullValidator("medicalHistory.hasSurgeries"))
			.append(new IsNotNullValidator("medicalHistory.weight"))
			.append(new IsNotNullValidator("medicalHistory.height"))
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

}
