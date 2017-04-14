package br.com.ackta.validation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class MinValidator extends FieldNameValidator {

	private static final String ERROR_CODE_PREFIX = "Min";
	private Double minValue;

	@Autowired
	public MinValidator(String fieldName1, Double minValue) {
		super(fieldName1);
		this.minValue = minValue;
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
				BigDecimal min = BigDecimal.valueOf(minValue);
				if (b1.compareTo(min) < 0) {
					reject  = true;
				}
			} else if (number instanceof BigInteger) {
				BigInteger b1 = (BigInteger) number;
				BigInteger min = BigInteger.valueOf(minValue.intValue());
				if (b1.compareTo(min) < 0) {
					reject = true;
				}
			} else {
				if (minValue.compareTo(number.doubleValue()) > 0) {
		            reject = true;
		        }
			}
			if (reject) {
				errors.rejectValue(fieldName, ERROR_CODE_PREFIX, 
						Arrays.array(minValue), ERROR_CODE_PREFIX);
			}
	    }
	}
}
