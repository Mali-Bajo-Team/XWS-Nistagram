package com.xws.adds.util.exceptions;

/*
 * Denotes that the request cannot be completed because it is in conflict with the current state 
 * of the resouce (for example, creating a resource that already exists). This will result in a 
 * HTTP 409 Conflict response.
 */
public class USConflictException extends UserServiceException {

	private static final long serialVersionUID = -4782673556690105083L;

	public USConflictException() {
		super();
	}

	public USConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public USConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public USConflictException(String message) {
		super(message);
	}

	public USConflictException(Throwable cause) {
		super(cause);
	}

}
