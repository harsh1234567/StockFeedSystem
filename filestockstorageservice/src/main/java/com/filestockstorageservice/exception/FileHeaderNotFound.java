package com.filestockstorageservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileHeaderNotFound extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a file header not found exception.
	 *
	 * @param message the message
	 */

	public FileHeaderNotFound(String message) {
		super(message);
	}

}