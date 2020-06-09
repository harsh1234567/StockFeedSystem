package com.usermanagementservice.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The ValidationFailureResponse.
 * 
 * @author harsh.jain
 *
 */
@Data
@NoArgsConstructor
public class ValidationFailureResponse {

	/** The fieldErros */
	private FieldErrorDto[] fieldErrors;

	/**
	 * The ValidationFailureResponse
	 * 
	 * @param FieldErrorDto fieldErrors.
	 */
	public ValidationFailureResponse(FieldErrorDto[] fieldErrors) {
		super();
		this.fieldErrors = fieldErrors;
	}
}
