package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.usermanagementservice.constants.Constants;

/**
 * The class AccountBalanceException.
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = Constants.AVAILABLE_QUANTITY_NOT_FOUND)
public class AccountBalanceException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a account balance exception.
	 *
	 * @param message the message
	 */
	public AccountBalanceException(String message) {
		super(message);
	}

}
