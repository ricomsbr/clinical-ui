package br.com.ackta.clinical.business.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ackta.clinical.model.entity.IPatient;
import br.com.ackta.clinical.presentation.Form;

public interface IPatientHelper {

	void delete(Long id);

	IPatient findOne(Long id);

	IPatient insert(Form form);

	Page<IPatient> search(Form form, Pageable pageable);

	IPatient update(Long id, Form form);

}
