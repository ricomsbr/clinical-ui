/*
 * CommonControllerAdviser.java		19/10/2015
 * 
 * Copyright (C) 2015 FAPESP. All Rights Reserved.
 */
package br.com.ackta.clinical.presentation;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * 
 * @author	RMendonca
 * @version @version@
 * @since	@since@
 */
@ControllerAdvice
class CommonControllerAdviser extends ResponseEntityExceptionHandler {
	@Autowired
	br.com.ackta.clinical.SerializableResourceBundleMessageSource messageSource;
	
//	@ExceptionHandler(value=ValidatorServiceException.class)
//	public ResponseEntity<Object> objectFoundExceptionHandler(ValidatorServiceException ex, WebRequest request) {
//		final HttpStatus responseStatus = ex.getErrors();
//		return defaultHandle(ex, request, responseStatus, ex.getArguments());
//	}
//	
//	@ExceptionHandler(value=ControllerException.class)
//	public ResponseEntity<Object> objectFoundExceptionHandler(ControllerException ex, WebRequest request) {
//		final HttpStatus responseStatus = ex.getStatusCode();
//		return defaultHandle(ex, request, responseStatus, ex.getArguments());
//	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> validationExceptionHandler(ConstraintViolationException ex, WebRequest request) {
		return defaultHandle(ex, request, HttpStatus.BAD_REQUEST, null);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> defaultExceptionHandler(Exception ex, WebRequest request) {
		final Object[] args = null;
		final String key = ex.getMessage();
		final String msg = messageSource.getMessage(key, args, 
				messageSource.getMessage(key, args, key, LocaleContextHolder.getLocale()),
				LocaleContextHolder.getLocale());
		logger.error("Exception message: " + msg, ex);
		final ResponseEntity<Object> result = this.handleException(ex, request);
		return result;
	}
	
	private ResponseEntity<Object> defaultHandle(
			Exception ex, WebRequest request, HttpStatus httpStatus, Object[] args) {
		final String key = ex.getMessage();
		final String msg = messageSource.getMessage(key, args, 
				messageSource.getMessage(key, args, key, LocaleContextHolder.getLocale()), 
				LocaleContextHolder.getLocale());
		logger.warn("Exception message: " + msg, ex);
		
//		final ErrorTO error = new ErrorTO(msg); // TODO remover esse comentario substituindo a linha abaixo.
//		return (this.handleExceptionInternal(ex, error,
//	              new HttpHeaders(), httpStatus, request));
		return (this.handleExceptionInternal(ex, null, new HttpHeaders(), httpStatus, request));
	}

}