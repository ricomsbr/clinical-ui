package br.com.ackta.clinical.business.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.presentation.Form;

public interface IPatientHelper {

	void delete(Long id);

	IPatient findOne(Long id);

	IPatient insert(Form form);

	Page<Patient> search(Form form, Pageable pageable);

	Patient update(Long id, Form form);

}
