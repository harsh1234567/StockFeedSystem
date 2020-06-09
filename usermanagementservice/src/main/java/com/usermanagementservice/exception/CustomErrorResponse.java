package com.usermanagementservice.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usermanagementservice.constants.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class CustomErrorResponse.
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {

	/** The errorCode */
	 /** The errorMsg */
	private String errorMsg;

	/** The status */
	private int status;

	/**
	 * the timestamp.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN)
	LocalDateTime timestamp;

	/**
	 * The default constructor.
	 * 
	 */
	public CustomErrorResponse(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}

}