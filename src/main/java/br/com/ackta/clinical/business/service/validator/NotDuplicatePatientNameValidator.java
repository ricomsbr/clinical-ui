package br.com.ackta.clinical.business.service.validator;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.clinical.data.repository.PersonalDataRepository;

@Service
public class NotDuplicatePatientNameValidator implements Validator {

	private static final String ERROR_CODE_PREFIX = "NotDuplicatedPatientName";
	private static final String DEFAULT_FIELD_NAME = "name";
	private PersonalDataRepository repository;

	@Autowired
	public NotDuplicatePatientNameValidator(PersonalDataRepository repository1) {
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
			errors.rejectValue(DEFAULT_FIELD_NAME, 
					ERROR_CODE_PREFIX, 
					Arrays.array(name), 
					ERROR_CODE_PREFIX);
		}
	}
}
