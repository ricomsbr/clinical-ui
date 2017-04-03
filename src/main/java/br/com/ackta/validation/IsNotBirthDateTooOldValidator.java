package br.com.ackta.validation;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

public class IsNotBirthDateTooOldValidator extends FieldNameValidator {

	private static final String ERROR_CODE_SUFIX = ".too_old";
	private static final long DEFAULT_MAX_AGE = 200L;
	private static final String DEFAULT_FIELD_NAME = "birthDate"; 
	private long maxAgeInYears = 200;

	@Autowired
	public IsNotBirthDateTooOldValidator(String fieldName1, long maxAgeInYears1) {
		super(fieldName1);
		Assert.isTrue(maxAgeInYears1 >= 0);
		this.maxAgeInYears = maxAgeInYears1;
	}

	@Autowired
	public IsNotBirthDateTooOldValidator(String fieldName1) {
		this(fieldName1, DEFAULT_MAX_AGE);
	}
	
	@Autowired
	public IsNotBirthDateTooOldValidator() {
		this(DEFAULT_FIELD_NAME);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return LocalDate.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LocalDate date = (LocalDate) target;
        if (Objects.nonNull(date)) {
        	if (LocalDate.now().minusYears(maxAgeInYears).isAfter(date)) {
	            errors.rejectValue(fieldName, fieldName + ERROR_CODE_SUFIX);
	        }
        }
    }


}
