package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class AuthenticationNotFound.
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AuthenticationNotFound extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a account balance exception.
	 *
	 * @param message the message
	 */
	public AuthenticationNotFound(String message) {
		super(message);
	}

}
