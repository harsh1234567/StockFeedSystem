package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.usermanagementservice.constants.Constants;

/**
 * The Class UserNotFound.
 * 
 * @author harsh.jain
 *
 **/
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = Constants.USER_NOT_FOUND)
public class UserNotFound  extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a new symbol not found exception.
	 *
	 * @param message the message
	 */
	public UserNotFound(String msg) {
		super(msg);
	}

}
