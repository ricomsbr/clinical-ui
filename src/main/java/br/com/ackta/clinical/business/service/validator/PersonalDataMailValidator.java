package br.com.ackta.clinical.business.service.validator;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
public class PersonalDataMailValidator implements Validator {

	private PersonalDataRepository repository;

	@Autowired
	public PersonalDataMailValidator(PersonalDataRepository repository1) {
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
		String mail = p.getMail();
		if (repository.findByMailIgnoreCase(mail).isPresent()) {
			String errorCode = "mail.already_exists";
			errors.rejectValue("mail", errorCode, Arrays.array(mail), errorCode);
		}
	}
}
