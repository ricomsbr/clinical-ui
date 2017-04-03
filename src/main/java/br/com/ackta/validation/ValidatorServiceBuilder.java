package br.com.ackta.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ValidatorServiceBuilder {

	public static ValidatorServiceBuilder build(Object validatable1, String objectName) {
		return new ValidatorServiceBuilder(validatable1, objectName);
	}
	private String objectName;
	private List<FieldNameValidator> fieldNameValidatorList;
	private List<Validator> validatorList;

	private Object validatable;

	/**
	 * @param objectName
	 */
	public ValidatorServiceBuilder(Object validatable1, String objectName) {
		super();
		this.objectName = objectName;
		fieldNameValidatorList = new ArrayList<>();
		validatorList = new ArrayList<>();
		validatable = validatable1;
	}

	public ValidatorServiceBuilder append(FieldNameValidator validator) {
		Assert.notNull(validatable);
		Assert.notNull(validator);
		Class<?> propertyType = findPropertyType(validatable, validator);
		if (!validator.supports(propertyType)) {
			String str = String.format("The supplied %s must support the validation of %s instances.",
					validator.getClass().getName(),
					propertyType);
			throw new IllegalArgumentException(str);
		}
		fieldNameValidatorList.add(validator);
		return this;
	}

	public ValidatorServiceBuilder append(Validator validator) {
		Assert.notNull(validatable);
		Assert.notNull(validator);
		Class<? extends Object> class1 = validatable.getClass();
		if (!validator.supports(class1)) {
			String str = String.format("The supplied %s must support the validation of %s instances.",
					validator.getClass().getName(),
					class1);
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
		for (FieldNameValidator validator: fieldNameValidatorList) {
			Object property = findProperty(validatable, validator);
			ValidationUtils.invokeValidator(validator, property, errors, validationHints);
		}
		for (Validator validator: validatorList) {
			ValidationUtils.invokeValidator(validator, validatable, errors, validationHints);
		}
		if(errors.hasErrors()) {
			throw new ValidatorServiceException(errors);
		}
	}

}
