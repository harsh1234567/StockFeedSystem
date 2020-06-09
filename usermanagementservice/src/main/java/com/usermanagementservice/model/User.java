package com.usermanagementservice.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class User
 * 
 * @author harsh.jain
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User implements Serializable {

	/**
	 * The new serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The id
	 */
	@Id
	private String id;

	/**
	 * The username
	 */
	@Field("username")
	@NotEmpty
	@NotNull
	private String username;

	/**
	 * The password
	 */
	@Field("password")
	@NotEmpty
	@NotNull
	private String password;

	/**
	 * The firstName
	 */
	@Field("firstName")
	@NotEmpty
	@NotNull
	private String firstName;

	/**
	 * The lastName
	 */
	@Field("lastName")
	@NotEmpty
	@NotNull
	private String lastName;

	/**
	 * The userId
	 */
	@Field("userId")
	@NotEmpty
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
	@NotEmpty
	@Email
	private String email;

	/** The roles. */
	@Field("roles")
	private List<String> roles;

	/**
	 * The active
	 */
	private boolean active = true;

	/**
	 * The enabled
	 */
	private boolean enabled;

}