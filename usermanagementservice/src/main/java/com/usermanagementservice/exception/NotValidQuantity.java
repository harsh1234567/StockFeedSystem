
package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class NotValidQuantity.
 * 
 * @author harsh.jain
 *
 **/
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotValidQuantity extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a Not valid Quantity exception.
	 *
	 * @param message the message
	 */
	public NotValidQuantity(String notValidQuantity) {
		super(notValidQuantity);
	}

}
