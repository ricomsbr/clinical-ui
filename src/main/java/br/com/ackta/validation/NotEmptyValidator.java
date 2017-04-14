package br.com.ackta.validation;

import java.util.Collection;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class NotEmptyValidator extends FieldNameValidator {

	private static final String ERROR_CODE_PREFIX = "NotEmpty";
	
	@Autowired
	public NotEmptyValidator(String fieldName1) {
		super(fieldName1);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Collection.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Collection<?> collection = (Collection<?>) target;
		if (collection.isEmpty()) {
			errors.rejectValue(fieldName,  
				ERROR_CODE_PREFIX, 
				Arrays.array(), 
				ERROR_CODE_PREFIX);
		}
    }
}
