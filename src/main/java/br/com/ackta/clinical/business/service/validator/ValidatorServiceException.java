package br.com.ackta.clinical.business.service.validator;

import org.springframework.validation.Errors;

/**
 *
 *
 * @author RMendonca
 * @version @version@
 * @since @since@
 */
public class ValidatorServiceException extends RuntimeException {

	private static final long serialVersionUID = -2921024874941524787L;

	private Errors errors;

	public ValidatorServiceException(Errors errors1) {
		super(errors1.getAllErrors().toString());
		this.errors = errors1;
	}

	public Errors getErrors() {
		return errors;
	}

}
