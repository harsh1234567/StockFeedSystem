package com.usermanagementservice.authservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagementservice.authModel.UserDetail;
import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.exception.UserNotFound;
import com.usermanagementservice.respository.UserRepository;
import com.usermanagementservice.security.model.Role;

import reactor.core.publisher.Mono;

/**
 * The class UserServiceAuthImpl
 * 
 * @author harsh.jain
 *
 */
@Service
public class UserServiceAuthImpl {

	/** The LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(UserServiceAuthImpl.class);

	/** The UserRepository */
	@Autowired
	UserRepository userRespository;

	/**
	 * The findByUserId.
	 * 
	 * @param String userId.
	 * 
	 * @return Mono
	 */
	public Mono<UserDetail> findByUserId(String userId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.FIND_BY_USER_ID_METHOD);
		StringBuilder userIdKey = new StringBuilder();
		userIdKey.append(Constants.USER_PREFIX);
		userIdKey.append(userId);
		return userRespository.findByuserId(userIdKey.toString()).flatMap(user -> {
			LOGGER.debug(LoggerConstants.USER_EXIST_WITH_ID + user.getId());
			UserDetail us = new UserDetail();
			us.setUsername(user.getUsername());
			us.setPassword(user.getPassword());
			List<Role> roles = new ArrayList<Role>();
			roles = user.getRoles().stream().map(r -> {
				if (r.equals(Constants.ADMIN)) {
					return Role.ROLE_ADMIN;
				} else {
					return Role.ROLE_USER;
				}
			}).collect(Collectors.toList());
			us.setRoles(roles);
			return Mono.just(us);
		}).switchIfEmpty(Mono.error(new UserNotFound(Constants.USER_NOT_FOUND)));
	}

}
