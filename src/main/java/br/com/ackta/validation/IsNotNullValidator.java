package br.com.ackta.validation;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class IsNotNullValidator extends FieldNameValidator {

	private static final String DOT = ".";
	private static final String ERROR_CODE_PREFIX = "NotNull";

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
        String errorCode = ERROR_CODE_PREFIX;
		ValidationUtils.rejectIfEmpty(errors, fieldName, errorCode, Arrays.array(target), errorCode + DOT + fieldName);
    }
}
