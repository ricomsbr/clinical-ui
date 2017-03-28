package br.com.ackta.clinical.business.service.validator;

import org.springframework.validation.Errors;

public class InvalidDataException extends RuntimeException {
    /**
	 *
	 */
	private static final long serialVersionUID = 6431750321285712480L;

	private Errors errors;

    public InvalidDataException(Errors errors) {
        super(errors.getAllErrors().toString());
        setErrors(errors);
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
