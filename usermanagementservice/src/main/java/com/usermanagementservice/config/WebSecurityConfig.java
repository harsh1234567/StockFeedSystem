package com.usermanagementservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.usermanagementservice.authsecurity.AuthenticationManager;
import com.usermanagementservice.authsecurity.SecurityContextRepository;
import com.usermanagementservice.constants.ApiEndpointsConstant;

import reactor.core.publisher.Mono;

/**
 * The class WebSecurityConfig.
 * 
 * @author harsh.jain
 *
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
	/**
	 * The AuthenticationManager.
	 * 
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * The SecurityContextRepository.
	 * 
	 */
	@Autowired
	private SecurityContextRepository securityContextRepository;

	/**
	 * The securitygWebFilterChain
	 * 
	 * @param ServerHttpSecurity http.
	 * 
	 * @return SecurityWebFilterChain.
	 */
	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
		return http.exceptionHandling().authenticationEntryPoint((auth, e) -> {
			return Mono.fromRunnable(() -> {
				auth.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			});
		}).accessDeniedHandler((auth, e) -> {
			return Mono.fromRunnable(() -> {
				auth.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
			});
		}).and().csrf().disable().formLogin().disable().httpBasic().disable()
				.authenticationManager(authenticationManager).securityContextRepository(securityContextRepository)
				.authorizeExchange().pathMatchers(HttpMethod.OPTIONS).permitAll()
				.pathMatchers(ApiEndpointsConstant.GET_TOKEN_API).permitAll()
				.pathMatchers(ApiEndpointsConstant.POST_CREATE_USER_API).permitAll().anyExchange().authenticated().and()
				.build();
	}
}
