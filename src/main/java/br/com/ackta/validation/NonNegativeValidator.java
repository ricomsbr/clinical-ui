package br.com.ackta.validation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class NonNegativeValidator extends FieldNameValidator {

	private static final String ERROR_CODE_PREFIX = "NonNegative";

	@Autowired
	public NonNegativeValidator(String fieldName1) {
		super(fieldName1);
	}

	@Override
	public boolean supports(Class<?> clazz1) {
		boolean result = (Integer.class.equals(clazz1)) ||
				(Long.class.equals(clazz1));
		return result;
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (Objects.nonNull(target)) {
			Number number = (Number) target;
			boolean reject = false;
			if (number instanceof BigDecimal) {
				BigDecimal b1 = (BigDecimal) number;
				BigDecimal b2 = BigDecimal.ZERO;
				if (b1.compareTo(b2) < 0) {
					reject  = true;
				}
			} else if (number instanceof BigInteger) {
				BigInteger b1 = (BigInteger) number;
				BigInteger b2 = BigInteger.ZERO;
				if (b1.compareTo(b2) < 0) {
					reject = true;
				}
			} else {
				Double zeroDouble = new Double(0d);
				if (zeroDouble.compareTo(number.doubleValue()) > 0) {
		            reject = true;
		        }
			}
			if (reject) {
				errors.rejectValue(fieldName, ERROR_CODE_PREFIX, 
						Arrays.array(target), ERROR_CODE_PREFIX);
			}
	    }
	}
}
