package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class AccountBalanceException.
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserOrderHistoryNotFound extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a account balance exception.
	 *
	 * @param message the message
	 */
	public UserOrderHistoryNotFound(String message) {
		super(message);
	}

}
