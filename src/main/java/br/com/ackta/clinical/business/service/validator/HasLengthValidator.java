package br.com.ackta.clinical.business.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class HasLengthValidator extends FieldNameValidator {

	private static final String ERROR_CODE_SUFIX = ".is_empty";
	
	@Autowired
	public HasLengthValidator(String fieldName1) {
		super(fieldName1);
	}
	
	@Autowired
	public HasLengthValidator() {
		super();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, fieldName + ERROR_CODE_SUFIX);
    }
}
