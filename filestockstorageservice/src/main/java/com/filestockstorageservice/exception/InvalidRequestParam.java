package com.filestockstorageservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class InvalidRequestParam.
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestParam extends RuntimeException {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a new invalid request param is not found exception.
	 *
	 * @param message the message
	 */
	public InvalidRequestParam(String message) {
		super(message);
	}

}