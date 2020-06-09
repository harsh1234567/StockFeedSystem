package com.usermanagementservice.authModel;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.usermanagementservice.security.model.Role;

import io.micrometer.core.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author harsh.jain
 *
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document
@Data
public class UserDetail implements UserDetails {
	/**
	 * The serialVersionUID.
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
	@NotNull
	private String username;

	/**
	 * The password
	 */
	@Field("password")
	@NotNull
	private String password;

	/**
	 * The firstName
	 */
	@Field("firstName")
	@NonNull
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

	
	/** The roles. */
	@Field("roles")
	private List<Role> roles;

	/**
	 * The active
	 */
	private boolean active = true;

	/**
	 * The enabled
	 */
	private boolean enabled;

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name()))
				.collect(Collectors.toList());
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

}