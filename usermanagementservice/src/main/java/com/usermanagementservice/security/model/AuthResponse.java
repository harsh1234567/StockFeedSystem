package com.usermanagementservice.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Class AuthResponse.
 * 
 * @author harsh.jain
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthResponse {

	/** The token */
	private String token;

}
