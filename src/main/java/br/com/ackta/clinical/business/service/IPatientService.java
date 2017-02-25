package br.com.ackta.clinical.business.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ackta.clinical.model.entity.IPatient;
import br.com.ackta.clinical.model.entity.Patient;

public interface IPatientService {

	void delete(Long id);

	Page<IPatient> findAll(Example<Patient> example, Pageable pageable);

	Optional<IPatient> findOne(Long id);

	IPatient insert(IPatient patient);

	IPatient update(IPatient patient);

}
