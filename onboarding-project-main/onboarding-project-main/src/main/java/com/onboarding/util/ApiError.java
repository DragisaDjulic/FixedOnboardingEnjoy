package com.onboarding.util;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {

	private int statusCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	ApiError(int status) {
		this();
		this.statusCode = status;
	}

	ApiError(int status, Throwable ex) {
		this();
		this.statusCode = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	ApiError(int status, String message, Throwable ex) {
		this();
		this.statusCode = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

}
