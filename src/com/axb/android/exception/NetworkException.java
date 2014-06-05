package com.axb.android.exception;

/**
 * Õ¯¬Á¡¨Ω”“Ï≥£
 *
 */
public class NetworkException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6337837107123308463L;

	public NetworkException() {
		super();
	}

	public NetworkException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public NetworkException(String detailMessage) {
		super(detailMessage);
	}

	public NetworkException(Throwable throwable) {
		super(throwable);
	}
	
	

}
