package br.com.ackta.clinical.business.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

public abstract class FieldNameValidator implements Validator {

	String fieldName;

	@Autowired
	public FieldNameValidator() {
		super();
		this.fieldName = "date";
	}
	
	@Autowired
	public FieldNameValidator(String fieldName1) {
		super();
		Assert.hasLength(fieldName1);
		this.fieldName = fieldName1;
	}

	public String getFieldName() {
		return fieldName;
	}

}
