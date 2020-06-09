package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class UserPortfolioException.
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class UserPortfolioException extends Throwable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a UserPortfoliException.
	 *
	 * @param message the message
	 */
	public UserPortfolioException(String message) {
		super(message);
	}

}
