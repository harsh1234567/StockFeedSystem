package com.usermanagementservice.exception;

import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The FieldErrorDto
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
public class FieldErrorDto {

	/** The field */
	private final String field;

	/** The message */
	private final Object message;

	/**
	 * Instantiates a FieldErrorDto.
	 *
	 * @param FieldError the fieldError
	 */
	public FieldErrorDto(FieldError fieldError) {
		this.field = fieldError.getField();
		this.message = fieldError.getDefaultMessage();
	}
}
