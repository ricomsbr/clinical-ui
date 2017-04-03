package br.com.ackta.clinical.business.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
public class PersonalDataValidator implements Validator {

	private PersonalDataRepository repository;

	@Autowired
	public PersonalDataValidator(PersonalDataRepository repository1) {
		super();
		this.repository = repository1;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return PersonalData.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PersonalData p = (PersonalData) target;
		if (repository.findFirstByCpf(p.getCpf()).isPresent()) {
			errors.rejectValue("cpf", "cpf.already_exists");
		}
		if (repository.findFirstByName(p.getName()).isPresent()) {
			errors.rejectValue("name", "name.already_exists");
		}
		if (repository.findFirstByMail(p.getMail()).isPresent()) {
			errors.rejectValue("mail", "mail.already_exists");
		}
	}
}
