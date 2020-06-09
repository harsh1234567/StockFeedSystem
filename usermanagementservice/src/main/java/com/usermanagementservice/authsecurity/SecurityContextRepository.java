package com.usermanagementservice.authsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;

import reactor.core.publisher.Mono;

/**
 * The class SecurityContextRepository.
 * 
 * @author harsh.jain
 *
 */
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SecurityContextRepository.class);

	/** The AuthenticationManager */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * The save
	 * 
	 * @param ServerWebExchange swe.
	 * 
	 * @param SecurityContext   sc.
	 * 
	 * @return Mono.
	 */
	@Override
	public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
		throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_EXCEPTION);
	}

	/**
	 * The load
	 * 
	 * @param ServerWebExcahnge swe.
	 * 
	 * @return Mono.
	 */
	@Override
	public Mono<SecurityContext> load(ServerWebExchange swe) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.LOAD_METHOD);
		ServerHttpRequest request = swe.getRequest();
		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith(Constants.BEARER)) {
			String authToken = authHeader.substring(7);
			Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
			return this.authenticationManager.authenticate(auth).map((authentication) -> {
				logger.debug(LoggerConstants.AUTHENTICATION_SUCCESS);
				return new SecurityContextImpl(authentication);
			});
		} else {
			return Mono.empty();
		}
	}

}
