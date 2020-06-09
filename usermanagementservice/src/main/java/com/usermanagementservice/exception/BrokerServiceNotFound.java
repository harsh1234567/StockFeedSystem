
package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class BrokerServiceNotFound.
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class BrokerServiceNotFound extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a broker service not found.
	 *
	 * @param message the message
	 */
	public BrokerServiceNotFound(String message) {
		super(message);
	}

}
