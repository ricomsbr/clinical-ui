package br.com.ackta.validation;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class NotBlankValidator extends FieldNameValidator {

	private static final String ERROR_CODE_PREFIX = "NotBlank";
	
	@Autowired
	public NotBlankValidator(String fieldName1) {
		super(fieldName1);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, 
				ERROR_CODE_PREFIX, 
				Arrays.array(target), 
				ERROR_CODE_PREFIX);
    }
}
