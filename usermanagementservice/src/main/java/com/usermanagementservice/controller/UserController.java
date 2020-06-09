package com.usermanagementservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagementservice.authsecurity.CustumPasswordEncoder;
import com.usermanagementservice.authsecurity.JWTUtil;
import com.usermanagementservice.authservice.UserServiceAuthImpl;
import com.usermanagementservice.constants.ApiEndpointsConstant;
import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.exception.UserPortfolioException;
import com.usermanagementservice.model.User;
import com.usermanagementservice.model.UserPortfolioResponse;
import com.usermanagementservice.model.response.UserResponse;
import com.usermanagementservice.security.model.AuthRequest;
import com.usermanagementservice.security.model.AuthResponse;
import com.usermanagementservice.service.UserService;

import reactor.core.publisher.Mono;

/**
 * The UserController
 * 
 * @author harsh.jain
 *
 */
@RestController
@RequestMapping(ApiEndpointsConstant.USER_API)
public class UserController {

	@Autowired
	private CustumPasswordEncoder passwordEncoder;

	/** The user service. */
	@Autowired
	private UserService userService;
	@Autowired
	private JWTUtil jWTUtil;

	/** The user userServiceImpl. */
	@Autowired
	private UserServiceAuthImpl userServiceAuthImpl;

	/** The Constant log. */
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * createUser.
	 *
	 * @param User the user
	 * 
	 * @return the mono
	 */
	@PostMapping(ApiEndpointsConstant.CREATE_USER_API)
	public Mono<UserResponse> createUser(@Valid @RequestBody User user, @RequestParam(required = false) String roles) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.CREATE_USER_CONTROLLER);
		Mono<UserResponse> response = userService.validateUser(user);
		return response.flatMap(res -> {
			logger.debug(LoggerConstants.CREATED_USER_WITH_ID + res.getUserId());
			return userService.createUser(user, roles);
		});
	}

	/**
	 * updateUser.
	 *
	 * @param User the user
	 * 
	 * @return the mono
	 */
	@PutMapping(ApiEndpointsConstant.GET_UPDATE_USER)
	@PreAuthorize(Constants.ROLE_USER)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Mono<UserResponse> updateUser(@RequestBody User user,
			@RequestParam(value = Constants.USER_ID) String userId) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.CREATE_USER_CONTROLLER);
		return userService.updateUser(user, userId);
	}

	/**
	 * getUserPortfolio.
	 *
	 * @param User the user
	 * 
	 * @return the Mono
	 */
	@GetMapping(ApiEndpointsConstant.GET_USER_PORTFOLIO_API)
	@PreAuthorize(Constants.ROLE_ADMIN_USER)
	public Mono<UserPortfolioResponse> getUserPortfolio(@RequestParam(Constants.USER_ID) String userId,
			@RequestParam(Constants.USER_NAME) String userName) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER_PORTOLIO_METHOD);
		return userService.getUserPortfolio(userId, userName);
	}

	/**
	 * The getPortfolio.
	 * 
	 * @param String userId.
	 * 
	 * @param String userName.
	 * 
	 * @return Mono.
	 * 
	 */
	@GetMapping(ApiEndpointsConstant.GET_PORTFOLIO_API)
	@PreAuthorize(Constants.ROLE_ADMIN_USER)
	public Mono<UserPortfolioResponse> getPortfolio(@RequestParam(Constants.USER_ID) String userId,
			@RequestParam(Constants.USER_NAME) String userName) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER_PORTOLIO_METHOD);
		return userService.getUserPortfolio(userId, userName).onErrorResume(error -> {
			return Mono.error(new UserPortfolioException(Constants.USER_PORTFOLIO_EXCEPTION + error));
		});
	}

	/**
	 * getAllUserPortfolio.
	 *
	 * @param String userId.
	 * 
	 * @return the Mono
	 */
	@GetMapping(ApiEndpointsConstant.GET_ALL_USER_PORTFOLIO_API)
	@PreAuthorize(Constants.ROLE_ADMIN)
	public Mono<List<UserPortfolioResponse>> getAllUserPortfolio(@RequestParam(required = false) String userId) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_ALL_USER_PORTFOLIO_METHOD);
		return userService.getAllUserPortfolio(userId).collectList().onErrorResume(error -> {
			return Mono.error(new UserPortfolioException(Constants.USER_PORTFOLIO_EXCEPTION + error));
		});
	}

	/**
	 * getAllUserPortfolio.
	 *
	 * @param String userId.
	 * 
	 * @return the Mono
	 */
	@PostMapping(ApiEndpointsConstant.GET_ACCESS_TOKEN_API)
	public Mono<ResponseEntity<?>> login(@Valid @RequestBody AuthRequest authRequest) {
		return userServiceAuthImpl.findByUserId(authRequest.getUserId()).map((user) -> {
			if (passwordEncoder.encode(authRequest.getPassword()).equals(user.getPassword())) {
				return ResponseEntity.ok(new AuthResponse(jWTUtil.generateToken(user)));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
