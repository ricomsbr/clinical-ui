package br.com.ackta.validation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class MaxValidator extends FieldNameValidator {

	private static final String ERROR_CODE_PREFIX = "Max";
	private Double maxValue;

	@Autowired
	public MaxValidator(String fieldName1, Double maxValue) {
		super(fieldName1);
		this.maxValue = maxValue;
	}

	@Override
	public boolean supports(Class<?> clazz1) {
		boolean result = Number.class.isAssignableFrom(clazz1);
		return result;
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (Objects.nonNull(target)) {
			Number number = (Number) target;
			boolean reject = false;
			if (number instanceof BigDecimal) {
				BigDecimal b1 = (BigDecimal) number;
				BigDecimal max = BigDecimal.valueOf(maxValue);
				if (b1.compareTo(max) > 0) {
					reject  = true;
				}
			} else if (number instanceof BigInteger) {
				BigInteger b1 = (BigInteger) number;
				BigInteger max = BigInteger.valueOf(maxValue.intValue());
				if (b1.compareTo(max) > 0) {
					reject = true;
				}
			} else {
				if (maxValue.compareTo(number.doubleValue()) < 0) {
		            reject = true;
		        }
			}
			if (reject) {
				errors.rejectValue(fieldName, ERROR_CODE_PREFIX, 
						Arrays.array(maxValue), ERROR_CODE_PREFIX);
			}
	    }
	}
}
