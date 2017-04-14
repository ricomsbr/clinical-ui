package br.com.ackta.validation;

import java.time.LocalDate;
import java.util.Objects;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class NotBeforeNowValidator extends FieldNameValidator {

	private static final String ERROR_CODE_PREFIX = "NotBeforeNow";

	@Autowired
	public NotBeforeNowValidator(String fieldName1) {
		super(fieldName1);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return LocalDate.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LocalDate d = (LocalDate) target;
        if (Objects.nonNull(d)) {
        	if (LocalDate.now().isBefore(d)) {
				errors.rejectValue(fieldName, ERROR_CODE_PREFIX, 
						Arrays.array(), ERROR_CODE_PREFIX);
	        }
        }
    }


}
