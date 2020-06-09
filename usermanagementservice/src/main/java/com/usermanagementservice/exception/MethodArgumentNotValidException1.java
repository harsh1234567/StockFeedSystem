package com.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The MethodArgumentNotValidException1.
 * 
 * @author harsh.jain
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MethodArgumentNotValidException1 extends RuntimeException {

	/**
	 * The serialVersionUID.
	 * 
	 **/
	private static final long serialVersionUID = 1L;

	/**
	 * The MethodArgumentNotValidException1.
	 * 
	 * @param String message.
	 */
	public MethodArgumentNotValidException1(String message) {
		super(message);
	}

}
