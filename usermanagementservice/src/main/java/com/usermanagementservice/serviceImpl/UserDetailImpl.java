package com.usermanagementservice.serviceImpl;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The Class UserDetailsImpl.
 *
 * @author harsh.jain
 *
 */
public class UserDetailImpl implements UserDetails {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 549285773358148945L;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The granted authorities. */
	private List<GrantedAuthority> grantedAuthorities;

	/** The enabled. */
	private boolean enabled;
	/**
	 * Instantiates a new user details impl.
	 *
	 * @param username    the username
	 * @param password    the password
	 * @param authorities the authorities
	 * @param enabled     the enabled
	 */
	public UserDetailImpl(String username, String password, String[] authorities, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
	}

	/**
	 * The getAuthorities
	 * 
	 * @return Collection GrantedAuthority.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	/**
	 * The getPassword
	 * 
	 * @return String.
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * The getUsername
	 * 
	 * @return String.
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * The isAccountNonExpired
	 * 
	 * @return String.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * The isAccountNonLocked
	 * 
	 * @return String.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * The isCredentialsNonExpired
	 * 
	 * @return String.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * The isEnabled
	 * 
	 * @return String.
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}
}