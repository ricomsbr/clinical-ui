package br.com.ackta.clinical.service.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ackta.clinical.model.entity.IPatient;
import br.com.ackta.clinical.presentation.Form;

public interface IPatientHelper {

	IPatient insert(Form form);

	IPatient update(Long id, Form form);

	Page<IPatient> search(Form form, Pageable pageable);

	IPatient findOne(Long id);

}
