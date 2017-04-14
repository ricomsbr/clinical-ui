package br.com.ackta.validation;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class NotNullValidator extends FieldNameValidator {

	private static final String ERROR_CODE_PREFIX = "NotNull";

	@Autowired
	public NotNullValidator(String fieldName1) {
		super(fieldName1);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, fieldName, ERROR_CODE_PREFIX, 
				Arrays.array(), ERROR_CODE_PREFIX);
    }
}
