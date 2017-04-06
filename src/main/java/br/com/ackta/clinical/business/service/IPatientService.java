package br.com.ackta.clinical.business.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ackta.clinical.data.entity.Patient;

public interface IPatientService {

	void delete(Long id);

	Page<Patient> findAll(Example<Patient> example, Pageable pageable);

	Optional<Patient> findOne(Long id);

	Patient validateAndSave(Patient patient);
	
	Patient update(Patient patient);
	
	void validate(Patient patient);

}
