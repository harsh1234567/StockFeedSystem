package com.usermanagementservice.authsecurity;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

/**
 * The AuthenticationManager.
 * 
 * @author harsh.jain
 *
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationManager.class);

	/**
	 * The jwtUtil.
	 */
	@Autowired
	private JWTUtil jwtUtil;

	/**
	 * The authenticate.
	 * 
	 * @param Authentication authentication.
	 * 
	 * @return Mono.
	 *
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Mono<Authentication> authenticate(Authentication authentication) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.AUTHENTICATE_METHOD);
		String authToken = authentication.getCredentials().toString();
		try {
			String username = jwtUtil.getUsernameFromToken(authToken);
			if (!jwtUtil.validateToken(authToken)) {
				return Mono.empty();
			}
			Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
			List<String> rolesMap = claims.get(Constants.ROLE, List.class);
			List<GrantedAuthority> authorities = new ArrayList<>();
			for (String rolemap : rolesMap) {
				authorities.add(new SimpleGrantedAuthority(rolemap));
			}
			return Mono.just(new UsernamePasswordAuthenticationToken(username, null, authorities));
		} catch (Exception e) {
			return Mono.empty();
		}
	}
}
