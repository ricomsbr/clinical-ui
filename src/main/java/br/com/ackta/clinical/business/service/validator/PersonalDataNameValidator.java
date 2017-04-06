package br.com.ackta.clinical.business.service.validator;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
public class PersonalDataNameValidator implements Validator {

	private PersonalDataRepository repository;

	@Autowired
	public PersonalDataNameValidator(PersonalDataRepository repository1) {
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
		String name = p.getName();
		if (repository.findByNameIgnoreCase(name).isPresent()) {
			String errorCode = "name.already_exists";
			errors.rejectValue("name", errorCode, Arrays.array(name), errorCode);
		}
	}
}
