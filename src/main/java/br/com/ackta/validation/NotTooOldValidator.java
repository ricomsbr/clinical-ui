package br.com.ackta.validation;

import java.time.LocalDate;
import java.util.Objects;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

public class NotTooOldValidator extends FieldNameValidator {

	private static final String ERROR_CODE_PREFIX = "NotTooOld";
	private static final long DEFAULT_MAX_AGE = 200L;
	private static final String DEFAULT_FIELD_NAME = "date"; 
	private long maxAgeInYears = 200;

	@Autowired
	public NotTooOldValidator(String fieldName1, long maxAgeInYears1) {
		super(fieldName1);
		Assert.isTrue(maxAgeInYears1 >= 0);
		this.maxAgeInYears = maxAgeInYears1;
	}

	@Autowired
	public NotTooOldValidator(String fieldName1) {
		this(fieldName1, DEFAULT_MAX_AGE);
	}
	
	@Autowired
	public NotTooOldValidator() {
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
				errors.rejectValue(fieldName, ERROR_CODE_PREFIX, 
						Arrays.array(target), ERROR_CODE_PREFIX);
	        }
        }
    }
}
