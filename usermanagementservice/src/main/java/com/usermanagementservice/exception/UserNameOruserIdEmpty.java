package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class UserNameOruserIdEmpty.
 * 
 * @author harsh.jain
 *
 **/
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNameOruserIdEmpty extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a new username or userId empty exception.
	 *
	 * @param message the message
	 */
	public UserNameOruserIdEmpty(String msg) {
		super(msg);
	}
}
