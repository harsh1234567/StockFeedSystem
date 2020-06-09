package com.filestockstorageservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InValidFileFound extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a new invalid file found exception.
	 *
	 * @param message the message
	 */

	public InValidFileFound(String message) {
		super(message);
	}

}