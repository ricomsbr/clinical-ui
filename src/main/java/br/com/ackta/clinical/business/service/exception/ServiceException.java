package br.com.ackta.clinical.business.service.exception;

import org.springframework.validation.Errors;

/**
 *
 *
 * @author RMendonca
 * @version @version@
 * @since @since@
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -2921024874941524787L;

	private Errors errors;

	public ServiceException(Errors errors1) {
		super(errors1.getAllErrors().toString());
		this.errors = errors1;
	}

	public Errors getErrors() {
		return errors;
	}

}
