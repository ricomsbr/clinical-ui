package br.com.ackta.validation;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class IsNotEmptyOrWhitespaceValidator extends FieldNameValidator {

	private static final String ERROR_CODE_SUFIX = ".is_empty";
	
	@Autowired
	public IsNotEmptyOrWhitespaceValidator(String fieldName1) {
		super(fieldName1);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
        String errorCode = fieldName + ERROR_CODE_SUFIX;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, errorCode, Arrays.array(target), errorCode);
    }
}
