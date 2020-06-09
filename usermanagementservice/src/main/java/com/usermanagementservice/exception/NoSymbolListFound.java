package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class NoSymbolListFound.
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NoSymbolListFound extends Throwable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -668096117498144556L;

	/**
	 * Instantiates a No SymbolList Found exception.
	 *
	 * @param message the message
	 */
	public NoSymbolListFound(String message) {
		super(message);
	}

}
