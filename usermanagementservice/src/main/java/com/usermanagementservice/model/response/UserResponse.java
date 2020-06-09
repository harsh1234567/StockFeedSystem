package com.usermanagementservice.model.response;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.couchbase.client.java.repository.annotation.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Instantiates a new user response.
 *
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {

	/**
	 * The new serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The username
	 */
	@Field("username")
	@NotNull
	private String username;

	/**
	 * The firstName
	 */
	@Field("firstName")
	@NotNull
	private String firstName;

	/**
	 * The lastName
	 */
	@Field("lastName")
	@NotNull
	private String lastName;

	/**
	 * The userId
	 */
	@Field("userId")
	@NotNull
	private String userId;

	/**
	 * The accountBalance
	 */
	@Field("accountBalance")
	private String accountBalance;

	/**
	 * The email
	 */
	@Field("email")
	@NotNull
	@Email
	private String email;
}
