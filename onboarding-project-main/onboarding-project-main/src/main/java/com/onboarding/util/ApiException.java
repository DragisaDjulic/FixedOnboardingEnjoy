package com.onboarding.util;


public class ApiException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int statusCode;

	public ApiException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}
	
}
