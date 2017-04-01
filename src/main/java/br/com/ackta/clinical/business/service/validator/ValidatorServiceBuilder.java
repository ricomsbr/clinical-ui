package br.com.ackta.clinical.business.service.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class ValidatorServiceBuilder {

	private String objectName;
	private List<FieldNameValidator> validatorList;
	private Object validatable;
	
	/**
	 * @param objectName
	 */
	public ValidatorServiceBuilder(Object validatable1, String objectName) {
		super();
		this.objectName = objectName;
		validatorList = new ArrayList<>();
		validatable = validatable1;
	}

	public static ValidatorServiceBuilder build(Object validatable1, String objectName) {
		return new ValidatorServiceBuilder(validatable1, objectName);
	}

	public ValidatorServiceBuilder append(FieldNameValidator validator) {
		Assert.notNull(validatable);
		Assert.notNull(validator);
		Class<?> propertyType = findPropertyType(validatable, validator);
		if (!validator.supports(propertyType)) {
			String str = String.format("The supplied %s must support the validation of %s instances.", 
					validator.getClass().getName(), 
					validatable.getClass().getName());
			throw new IllegalArgumentException(str);
		}
		validatorList.add(validator);
		return this;
	}
	
	/**
	 * @param validatable1
	 * @param v
	 * @return
	 */
	private Object findProperty(Object validatable1, FieldNameValidator v) {
		Object property = null;
		try {
			property = PropertyUtils.getProperty(validatable1, v.getFieldName());
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		return property;
	}

	/**
	 * @param validatable1
	 * @param v
	 * @return
	 */
	private Class<?> findPropertyType(Object validatable1, FieldNameValidator v) {
		Class<?> propertyType = null;
		try {
			propertyType = PropertyUtils.getPropertyType(validatable1, v.getFieldName());
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		return propertyType;
	}
	
	public void validate() throws ValidatorServiceException {
		validate((Object[]) null); 
	}
	
	/**
	 * @param validationHints one or more hint objects to be passed to the validation engine
	 * @throws ValidatorServiceException
	 */
	public void validate(Object... validationHints) throws ValidatorServiceException {	
		Errors errors = null;
		if (Objects.isNull(errors)) {
			errors = new BindException(validatable, objectName);
		}
		for (FieldNameValidator validator: validatorList) {
			Object property = findProperty(validatable, validator);
			ValidationUtils.invokeValidator(validator, property, errors, validationHints);
		}
		if(errors.hasErrors()) {
			throw new ValidatorServiceException(errors);
		}
	}

}
