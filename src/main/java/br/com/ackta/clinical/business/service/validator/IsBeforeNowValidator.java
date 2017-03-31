package br.com.ackta.clinical.business.service.validator;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class IsBeforeNowValidator implements Validator {

	@Autowired
	public IsBeforeNowValidator() {
		super();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return LocalDate.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LocalDate d = (LocalDate) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");
        ValidationUtils.rejectIfEmpty(errors, "birthDate", "birthDate.empty");
        if (Objects.nonNull(d)) {
        	if (LocalDate.now().isBefore(d)) {
	            errors.rejectValue("birthDate", "birthDate.after_now");
	        }
        }
    }


}
