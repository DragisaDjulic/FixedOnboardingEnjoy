package com.onboarding.util;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<Object> handleApiError(ApiException e) {
		ApiError apiErr = new ApiError(e.statusCode);
		apiErr.setMessage(e.getMessage());
		logger.error(e.statusCode +" " + e.getMessage());
		return new ResponseEntity<>(apiErr, HttpStatus.valueOf(apiErr.getStatusCode()));
	}
	
}
