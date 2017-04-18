package br.com.ackta.clinical.business.helper;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.presentation.Form;

public interface IPatientHelper {

	void delete(Long id);

	Form findOne(Long id);

	IPatient insert(Form form);

	Patient update(Long id, Form form);

	byte[] generatePdf(Long id);

	Page<Patient> search(String name, String cpf, String rg, Gender gender, LocalDate birthDate, Pageable pageable);

}
