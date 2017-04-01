package br.com.ackta.clinical.business.service.validator;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class IsDateAfterNowValidator extends FieldNameValidator {

	private static final String ERROR_CODE_SUFIX = ".after_now";

	@Autowired
	public IsDateAfterNowValidator() {
		super();
	}
	
	@Autowired
	public IsDateAfterNowValidator(String fieldName1) {
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
	            errors.rejectValue(fieldName, fieldName + ERROR_CODE_SUFIX);
	        }
        }
    }


}
