package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.usermanagementservice.constants.Constants;

/**
 * The class SellOrderNotFound.
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = Constants.STOCK_NOT_FOUND)
public class SellOrderNotFound extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a sell order not found exception.
	 *
	 * @param message the message
	 */
	public SellOrderNotFound(String message) {
		super(message);
	}
}
