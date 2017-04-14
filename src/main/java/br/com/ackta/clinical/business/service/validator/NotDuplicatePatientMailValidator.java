package br.com.ackta.clinical.business.service.validator;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
public class NotDuplicatePatientMailValidator implements Validator {

	private static final String ERROR_CODE_PREFIX = "NotDuplicatePatientMail";
	private static final String DEFAULT_FIELD_NAME = "mail";
	private PersonalDataRepository repository;

	@Autowired
	public NotDuplicatePatientMailValidator(PersonalDataRepository repository1) {
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
		if (repository.findFirstByMailIgnoreCase(mail).isPresent()) {
			errors.rejectValue(DEFAULT_FIELD_NAME,
					ERROR_CODE_PREFIX,
					Arrays.array(mail),
					ERROR_CODE_PREFIX);
		}
	}
}
