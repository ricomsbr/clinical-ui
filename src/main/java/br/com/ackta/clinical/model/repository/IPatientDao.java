package br.com.ackta.clinical.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ackta.clinical.model.entity.IPatient;
import br.com.ackta.clinical.model.entity.Patient;

public interface IPatientDao {

	IPatient insert(IPatient patient);

	IPatient update(IPatient patient);

	Page<IPatient> findAll(Example<Patient> example, Pageable pageable);

	Optional<IPatient> findOne(Long id);

}
