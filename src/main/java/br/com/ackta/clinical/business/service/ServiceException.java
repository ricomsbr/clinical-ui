package br.com.ackta.clinical.business.service;

public class ServiceException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2359641954807294609L;

	/**
	 * 
	 */
	public ServiceException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ServiceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServiceException(String arg0) {
		super(arg0, null);
	}
	
	/**
	 * @param arg0
	 */
	public ServiceException(Throwable arg0) {
		super(arg0);
	}
	
	
}
