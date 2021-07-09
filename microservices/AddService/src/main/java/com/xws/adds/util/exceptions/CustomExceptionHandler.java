package com.xws.adds.util.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder details = new StringBuilder();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.append(error.getDefaultMessage());
			details.append(" ");
		}
		ErrorResponse error = new ErrorResponse("Request parameters are not valid.", details.toString());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(USAuthenticationException.class)
	public ResponseEntity<Object> badRequest(USAuthenticationException ex) {
		ErrorResponse response = new ErrorResponse("Bad credentials.", ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(USConflictException.class)
	public ResponseEntity<Object> alreadyExists(USConflictException ex) {
		ErrorResponse response = new ErrorResponse("The request is in conflict with the current state of the resource.",
				ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(USAuthorizationException.class)
	public ResponseEntity<Object> alreadyExists(USAuthorizationException ex) {
		ErrorResponse response = new ErrorResponse("You do not have permission to access the requested resource.",
				ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
	}

}