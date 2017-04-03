package br.com.ackta.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class IsNotNullValidator extends FieldNameValidator {

	private static final String ERROR_CODE_SUFIX = ".is_null";

	@Autowired
	public IsNotNullValidator() {
		super();
	}

	@Autowired
	public IsNotNullValidator(String fieldName1) {
		super(fieldName1);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, fieldName, fieldName + ERROR_CODE_SUFIX);
    }
}
