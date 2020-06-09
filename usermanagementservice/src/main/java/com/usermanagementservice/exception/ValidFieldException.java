package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class UserNotFound.
 * 
 * @author harsh.jain
 *
 **/
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidFieldException  extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a new symbol not found exception.
	 *
	 * @param message the message
	 */
	public ValidFieldException(String msg) {
		super(msg);
	}

}
