package br.com.ackta.validation;

import java.time.LocalDate;
import java.util.Objects;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class IsNotDateBeforeNowValidator extends FieldNameValidator {

	private static final String ERROR_CODE_SUFIX = ".not_before_now";

	@Autowired
	public IsNotDateBeforeNowValidator(String fieldName1) {
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
	            String errorCode = fieldName + ERROR_CODE_SUFIX;
				errors.rejectValue(fieldName, errorCode, Arrays.array(target), errorCode);
	        }
        }
    }


}
