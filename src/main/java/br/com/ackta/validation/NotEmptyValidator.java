package br.com.ackta.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		return findInInterfaces(clazz, Collection.class);
	}

	private boolean findInInterfaces(Class<?> source, Class<?> target) {
		List<Class<?>> allInterfaces = findAllInterfaces(source, new ArrayList<>());
		return allInterfaces.contains(target);
	}
	
	private List<Class<?>> findAllInterfaces(Class<?> clazz, List<Class<?>> result) {
		for (Class<?> interf : clazz.getInterfaces()) {
			if (!result.contains(interf)) {
				result.add(interf);
				result = findAllInterfaces(interf, result);
			}
		}
		return result;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Collection<?> collection = (Collection<?>) target;
		if (collection.isEmpty()) {
			errors.rejectValue(fieldName,  
				ERROR_CODE_PREFIX, 
				Arrays.array(target), 
				ERROR_CODE_PREFIX);
		}
    }
}
