package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * the class GlobalException
 * 
 * @author harsh.jain
 *
 */
public class GlobalException extends ResponseStatusException {

	/**
	 * The parametric constructor with two args GlobalException
	 * 
	 * @param HttpStatus status
	 * 
	 * @param String     message
	 * 
	 */
	public GlobalException(HttpStatus status, String message) {
		super(status, message);
	}

	/**
	 * The parametric constructor with three args GlobalException
	 * 
	 * @param HttpStatus status
	 * 
	 * @param String     message
	 * 
	 * @param Throwable  e
	 * 
	 */
	public GlobalException(HttpStatus status, String message, Throwable e) {
		super(status, message, e);
	}
}