package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.usermanagementservice.constants.Constants;

/**
 * The class UserAlreadyExist
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = Constants.USER_ALREADY_EXIST)
public class UserAlreadyExist extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a User Already Exist.
	 *
	 * @param message the message
	 */
	public UserAlreadyExist(String msg) {
		super(msg);
	}

}
