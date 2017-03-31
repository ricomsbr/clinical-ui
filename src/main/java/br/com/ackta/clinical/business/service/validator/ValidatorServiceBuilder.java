package br.com.ackta.clinical.business.service.validator;

import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import org.assertj.core.util.Lists;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.ackta.clinical.business.service.exception.ServiceException;

public class ValidatorServiceBuilder {

	public LinkedMultiValueMap<Object, Validator> validatorMap;

	public void append(Object validatable, Validator... validators) {
		Assert.notNull(validatable);
		Assert.noNullElements(validators);
		String str = Lists.newArrayList(validators)
			.stream()
			.filter(v -> v.supports(validatable.getClass()))
			.map(v -> String.format("The supplied s% must support the validation of %s instances.", v.getClass().getName(), validatable.getClass().getName()))
			.reduce("", (s1, s2) -> s1 + "\n" + s2 );
		if (!str.isEmpty()) {
			throw new IllegalArgumentException(str);
		}
		validatorMap.put(validatable, Lists.newArrayList(validators));
	}

	public ValidatorServiceBuilder build() {
		return new ValidatorServiceBuilder();
	}

	public void validate() throws ServiceException {
		Errors errors = null;
		for (Entry<Object, List<Validator>> entry : validatorMap.entrySet()) {
			Object validatable = entry.getKey();
			BindException bindException = new BindException(validatable, validatable.getClass().getName());
			for (Validator v : entry.getValue()) {
				ValidationUtils.invokeValidator(v, validatable, bindException);
				if (Objects.isNull(errors)) {
					errors = bindException;
				} else {
					errors.addAllErrors(bindException);
				}
			};
		}
		if(!errors.getAllErrors().isEmpty()) {
			throw new ServiceException(errors);
		}
	}
//
//	private Errors validate(Object validatable, Validator validator)  {
//		String name = validatable.getClass().getName();
//		BindException bindException = new BindException(validatable, name);
//		ValidationUtils.invokeValidator(validator, validatable, bindException );
//		return bindException.getAllErrors();
//
//
//	}
}
