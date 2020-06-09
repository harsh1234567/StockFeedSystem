package com.usermanagementservice.service;

import java.util.List;

import com.usermanagementservice.model.OrderInfo;
import com.usermanagementservice.model.User;
import com.usermanagementservice.model.UserPortfolioResponse;
import com.usermanagementservice.model.response.UserResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface UserService.
 *
 * @author harsh.jain
 *
 */

public interface UserService {

	/**
	 * User createUser.
	 *
	 * @param user  the user
	 * @param roles
	 * @return the mono
	 */
	Mono<UserResponse> createUser(User user, String roles);

	/**
	 * call Validate user.
	 *
	 * @param user the user
	 * @return Mono
	 */
	Mono<UserResponse> validateUser(User user);

	/**
	 * Find user.
	 *
	 * @param userId the user id
	 * 
	 * @return Mono
	 */
	Mono<User> getUser(String userId);

	/**
	 * Find getUserName.
	 *
	 * @param userId the user id
	 * 
	 * @return Mono
	 */
	Mono<User> getUserName(String userId);

	/**
	 * getAllUserPortfolio.
	 *
	 * @param userId.
	 * 
	 * @return Flux
	 */
	Flux<UserPortfolioResponse> getAllUserPortfolio(String userId);

	/**
	 * getUserPortfolio.
	 *
	 * @param userId
	 * 
	 * @param userName
	 * 
	 * @return Mono
	 */
	Mono<UserPortfolioResponse> getUserPortfolio(String userId, String userName);

	/**
	 * OrderInfo.
	 *
	 * @param userId
	 * 
	 * @param userName
	 * 
	 * @return Mono
	 */
	Mono<List<OrderInfo>> getPortfolio(String userId, String userName);

	/**
	 * updateUser.
	 *
	 * @param User   user.
	 * 
	 * @param String userId.
	 * 
	 * @return Mono
	 */
	Mono<UserResponse> updateUser(User user, String userId);

}
