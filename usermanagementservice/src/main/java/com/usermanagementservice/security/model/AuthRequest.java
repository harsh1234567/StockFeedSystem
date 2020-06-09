package com.usermanagementservice.security.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Class AuthRequest.
 * 
 * @author harsh.jain
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequest {
	/** The userId */
	@NotEmpty
	@NotNull
	private String userId;

	/** The password */
	@NotEmpty
	@NotNull
	private String password;

}
