package com.usermanagementservice.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.usermanagementservice.authsecurity.CustumPasswordEncoder;
import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.exception.InvalidNumericeFound;
import com.usermanagementservice.exception.SymbolNotFound;
import com.usermanagementservice.exception.UserAlreadyExist;
import com.usermanagementservice.exception.UserNameOruserIdEmpty;
import com.usermanagementservice.exception.UserNotFound;
import com.usermanagementservice.model.OrderInfo;
import com.usermanagementservice.model.User;
import com.usermanagementservice.model.UserPortfolio;
import com.usermanagementservice.model.UserPortfolioResponse;
import com.usermanagementservice.model.response.UserResponse;
import com.usermanagementservice.respository.OrderDetailsRespository;
import com.usermanagementservice.respository.UserRepository;
import com.usermanagementservice.service.UserService;
import com.usermanagementservice.utils.CommonUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The UserServiceImpl
 * 
 * @author harsh.jain
 *
 */
@Service
public class UserServiceImpl implements UserService {

	/** The Constant log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	/** The userRepo. */
	@Autowired
	private UserRepository userRepository;

	/** The orderDetailsRespository. */
	@Autowired
	private OrderDetailsRespository orderDetailsRespository;

	/** The passwordEncoder. */
	@Autowired
	private CustumPasswordEncoder passwordEncoder;

	/** The userPortfolioServiceImpl. */
	@Autowired
	private UserPortfolioServiceImpl userPortfolioServiceImpl;

	/**
	 * call Validate user.
	 *
	 * @param user the user
	 *
	 * @return the mono
	 */
	@Override
	public Mono<UserResponse> validateUser(User user) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.VALIDATE_USER_METHOD);
		return Mono.just(validate(user));
	}

	/**
	 * getUserPortfolio.
	 *
	 * @param userId
	 * 
	 * @param userName
	 * 
	 * @param adminId
	 * 
	 * @return Mono
	 */
	@Override
	public Mono<UserPortfolioResponse> getUserPortfolio(String userId, String userName) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER_PORTFOLIO_METHOD);
		return orderDetailsRespository.findByIdPortfolio(porfolioKey(userId, userName)).flatMap(portfolioDetails -> {
			LOGGER.debug(LoggerConstants.GET_USER_PORTFOLIO, portfolioDetails.getId());
			UserPortfolioResponse userPortfolioResponse = new UserPortfolioResponse();
			List<OrderInfo> userportfolioList = new ArrayList<OrderInfo>();
			setUserPortfolioResponse(portfolioDetails, userPortfolioResponse, userportfolioList);
			return Mono.just(userPortfolioResponse);
		}).doOnError(error -> LOGGER.error(LoggerConstants.ERROR_MESSAGE_GET_USER_PORTFOLIO))
				.switchIfEmpty(Mono.error(new UserNotFound(Constants.USER_NOT_FOUND)));
	}

	/**
	 * The setUserPortfolioResponse.
	 * 
	 * @param UserPortfolio         portfolioDetails.
	 * 
	 * @param UserPortfolioResponse userPortfolioResponse.
	 * 
	 * @param OrderInfo             userportfolioList.
	 * 
	 */
	private void setUserPortfolioResponse(UserPortfolio portfolioDetails, UserPortfolioResponse userPortfolioResponse,
			List<OrderInfo> userportfolioList) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.SET_USER_PORTFOLIO_RESPONSE);
		if (ObjectUtils.isEmpty(portfolioDetails.getId())) {
			userPortfolioResponse.setId(portfolioDetails.getId());
		} else {
			userPortfolioResponse.setId(portfolioDetails.getId());
		}
		if (ObjectUtils.isEmpty(portfolioDetails.getListOrderInfo())) {
			userPortfolioResponse.setUserName(Constants.EMPTY);
		} else {
			userPortfolioResponse.setUserName(portfolioDetails.getUserName());
		}
		if (ObjectUtils.isEmpty(portfolioDetails.getListOrderInfo())) {
			userPortfolioResponse.setOrderInfo(userportfolioList);
		} else {
			userPortfolioResponse.setOrderInfo(portfolioDetails.getListOrderInfo());
		}
		if (ObjectUtils.isEmpty(portfolioDetails.getInvestment())) {
			userPortfolioResponse.setInvestment(Constants.ZERO_DOUBLE_VALUE);
		} else {
			userPortfolioResponse.setInvestment(portfolioDetails.getInvestment());
		}
		if (ObjectUtils.isEmpty(portfolioDetails.getIncome())) {
			userPortfolioResponse.setIncome(Constants.ZERO_DOUBLE_VALUE);
		} else {
			userPortfolioResponse.setIncome(portfolioDetails.getIncome());
		}
		if (ObjectUtils.isEmpty(portfolioDetails.getEarning())) {
			userPortfolioResponse.setEarning(Constants.ZERO_DOUBLE_VALUE);
		} else {
			userPortfolioResponse.setEarning(portfolioDetails.getEarning());
		}
		if (ObjectUtils.isEmpty(portfolioDetails.getTotalProfit())) {
			userPortfolioResponse.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
		} else {
			userPortfolioResponse.setTotalProfit(portfolioDetails.getTotalProfit());
		}
		if (ObjectUtils.isEmpty(portfolioDetails.getTotalLoss())) {
			userPortfolioResponse.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
		} else {
			userPortfolioResponse.setTotalLoss(portfolioDetails.getTotalLoss());
		}
		if (ObjectUtils.isEmpty(portfolioDetails.getOverAllProfit())) {
			userPortfolioResponse.setOverAllProfit(Constants.ZERO_DOUBLE_VALUE);
		} else {
			userPortfolioResponse.setOverAllProfit(portfolioDetails.getOverAllProfit());
		}
		if (ObjectUtils.isEmpty(portfolioDetails.getAccountBalance())) {
			userPortfolioResponse.setAccountBalance(Constants.ZERO_DOUBLE_VALUE);
		} else {
			userPortfolioResponse.setAccountBalance(portfolioDetails.getAccountBalance());
		}
		userPortfolioResponse.setCount(String.valueOf(portfolioDetails.getListOrderInfo().size()));
	}

	/**
	 * getAllUserPortfolio.
	 *
	 * @param userId
	 * 
	 * @return Flux
	 */
	@Override
	public Flux<UserPortfolioResponse> getAllUserPortfolio(String userId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_ALL_USER_PORTFOLIO_METHOD);
		String userPortFolioId = Constants.PORTFOLIO_PERFIX + Constants.PERCENTAGE;
		return orderDetailsRespository.findByIdAllPortfolio(userPortFolioId).flatMap(portfolioDetails -> {
			LOGGER.debug(LoggerConstants.GET_ALL_USER_PORTFOLIO, portfolioDetails.getId());
			UserPortfolioResponse userPortfolioResponse = new UserPortfolioResponse();
			List<OrderInfo> userportfolioList = new ArrayList<OrderInfo>();
			setUserPortfolioResponse(portfolioDetails, userPortfolioResponse, userportfolioList);
			return Flux.just(userPortfolioResponse);
		}).doOnError(error -> LOGGER.error(LoggerConstants.ERROR_GET_ALL_USER_PORTFOLIO));
	}

	/**
	 * Find user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	@Override
	public Mono<User> getUser(String userId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER_METHOD);
		StringBuilder userIdKey = new StringBuilder();
		userIdKey.append(Constants.USER_PREFIX);
		userIdKey.append(userId);
		return userRepository.findByuserId(userIdKey.toString())
				.switchIfEmpty(Mono.error(new UserNotFound(Constants.USER_NOT_FOUND)));
	}

	/**
	 * Create user if not exist.
	 *
	 * @param user    the user
	 * @param isFound the is found
	 * @param roles
	 * @return the mono
	 */
	private Mono<UserResponse> createIfNotExist(User user, boolean isFound, String roles) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.CREATE_IF_NOT_EXIST);
		final String userId = Constants.USER_PREFIX + user.getUserId();
		if (!isFound) {
			LOGGER.info(LoggerConstants.SAVE_DATA, user.getUsername());
			user.setId(userId);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if (roles != null && roles.toUpperCase().equals(Constants.ADMIN)) {
				user.setRoles(Arrays.asList(Constants.ADMIN));
			} else if (roles != null && roles.toUpperCase().equals(Constants.USER)) {
				user.setRoles(Arrays.asList(Constants.USER));
			} else {
				user.setRoles(Arrays.asList(Constants.ADMIN, Constants.USER));
			}
			user.setAccountBalance(Constants.ZERO_DOUBLE_VALUE);
			return userRepository.save(user).flatMap(savedUser -> {
				LOGGER.debug(LoggerConstants.GET_USER_ID, savedUser.getId());
				UserResponse userResponse = new UserResponse();
				List<OrderInfo> ordrInfo = new ArrayList<OrderInfo>();
				LOGGER.info(LoggerConstants.CREAE_PORTFOLIO_USER);
				orderDetailsRespository.updatePortfolioOrder(savedUser, ordrInfo, Constants.ZERO_DOUBLE_VALUE,
						Constants.ZERO_DOUBLE_VALUE, Constants.ZERO_DOUBLE_VALUE, Constants.ZERO_DOUBLE_VALUE,
						Constants.ZERO_DOUBLE_VALUE, Constants.ZERO_DOUBLE_VALUE, Constants.ZERO_DOUBLE_VALUE);
				userResponse.setFirstName(savedUser.getFirstName());
				userResponse.setLastName(savedUser.getLastName());
				userResponse.setEmail(savedUser.getEmail());
				userResponse.setUserId(savedUser.getUserId());
				userResponse.setAccountBalance(savedUser.getAccountBalance());
				userResponse.setUsername(savedUser.getUsername());
				return Mono.just(userResponse);
			}).doOnError(err -> LOGGER.error(Constants.ERROR_WHILE_CREATING_USER));
		}
		LOGGER.error(LoggerConstants.USER_INVALID, user.getUsername());
		return Mono.error(new UserAlreadyExist(Constants.USER_ALREADY_EXIST));
	}

	/**
	 * Validate user.
	 *
	 * @param user the user
	 * 
	 * @return the user response
	 */
	private UserResponse validate(User user) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.VALIDATE_METHOD);
		UserResponse res = new UserResponse();
		if (CommonUtils.emptyString(user.getUsername())) {
			Mono.error(new UserNameOruserIdEmpty(Constants.USER_NAME_USER_ID_ERROR));
		} else if (CommonUtils.emptyString(user.getUserId())) {
			Mono.error(new UserNameOruserIdEmpty(Constants.USER_NAME_USER_ID_ERROR));
		}
		return res;
	}

	/**
	 * porfolioKey.
	 *
	 * @param userId
	 * 
	 * @param userName
	 * 
	 * 
	 * @return String
	 */
	private String porfolioKey(String userId, String userName) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.PORFOLIO_USER_ID);
		StringBuilder porfolioKey = new StringBuilder(Constants.PORTFOLIO_PERFIX);
		porfolioKey.append(userName).append(Constants.COLON_COLON).append(userId);
		return porfolioKey.toString();
	}

	/**
	 * getPortfolio.
	 * 
	 * @param String userId
	 * 
	 * @param String userName
	 *
	 * @return Mono.
	 */
	@Override
	public Mono<List<OrderInfo>> getPortfolio(String userId, String userName) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_PORTFOLIO_METHOD);
		StringBuilder portfolioId = new StringBuilder();
		portfolioId.append(Constants.PORTFOLIO_PERFIX);
		portfolioId.append(userName);
		portfolioId.append(Constants.COLON_COLON);
		portfolioId.append(userId);
		return orderDetailsRespository.findByIdPortfolio(portfolioId.toString()).flatMap(orders -> {
			LOGGER.debug(LoggerConstants.ORDER_KEY + orders.getId());
			if (orders.getListOrderInfo().isEmpty()) {
				return Mono.error(new SymbolNotFound(Constants.NO_STOCK_FOUND_IN_PORTFOLIO));
			}
			return Mono.just(orders.getListOrderInfo());
		}).switchIfEmpty(Mono.error(new UserNotFound(Constants.USER_NOT_FOUND)));
	}

	/**
	 * updateUser.
	 * 
	 * @param String userId
	 * 
	 * @return Mono.
	 */
	@Override
	public Mono<User> getUserName(String userId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER_NAME_METHOD);
		return userRepository.findByuserName(userId).doOnSuccess(success -> {
			LOGGER.info(LoggerConstants.SUCCESS_GET_USERNAME + success);
		}).doOnError(error -> {
			LOGGER.info(LoggerConstants.ERROR_USER_NAME + error);
		});
	}

	/**
	 * updateUser.
	 * 
	 * @param String userId
	 * 
	 * @param User   user
	 * 
	 * @return Mono.
	 */
	@Override
	public Mono<UserResponse> updateUser(User user, String userId) {
		user.setUserId(userId);
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.CREATE_USER_METHOD);
		StringBuilder userIdkey = new StringBuilder();
		userIdkey.append(Constants.USER_PREFIX);
		userIdkey.append(user.getUserId());
		return userRepository.existsById(userIdkey.toString()).flatMap(isFound -> updateUserIfExist(user, isFound))
				.doOnError(error->LOGGER.error(LoggerConstants.UPDATE_USER_ERROR));
	}

	/**
	 * updateUserIfExist.
	 * 
	 * @param User    user.
	 * 
	 * @param Boolean isFound.
	 * 
	 * @return Mono.
	 */
	private Mono<UserResponse> updateUserIfExist(User user, Boolean isFound) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.UPDATE_USER_IF_EXIST_METHOD);
		StringBuilder userIdKey = new StringBuilder();
		userIdKey.append(Constants.USER_PREFIX);
		userIdKey.append(user.getUserId());
		return userRepository.findByuserId(userIdKey.toString()).flatMap(getUser -> {
			LOGGER.debug(LoggerConstants.GET_UPDATE_USER + getUser.getId());
			UserResponse userResponse = new UserResponse();
			LOGGER.info(LoggerConstants.SAVE_DATA, user.getUsername());
			user.setId(userIdKey.toString());
			final User updateUser = new User();
			updateUser.setAccountBalance(getUser.getAccountBalance());
			updateUser.setLastName(getUser.getLastName());
			updateUser.setId(getUser.getId());
			updateUser.setFirstName(getUser.getFirstName());
			updateUser.setEmail(getUser.getEmail());
			updateUser.setUsername(getUser.getUsername());
			updateUser.setUserId(getUser.getUserId());
			updateUser.setRoles(getUser.getRoles());
			updateUser.setPassword(getUser.getPassword());
			UserPortfolioResponse userPortfolioResponse = userPortfolioServiceImpl
					.getPortfolioUser(CommonUtils.portfolioUserId(updateUser));
			Double updatedAccount = Double.parseDouble(userPortfolioResponse.getAccountBalance());
			if (!ObjectUtils.isEmpty(user.getAccountBalance())
					&& !(user.getAccountBalance().matches(Constants.NUMERIC_PATTERN))) {
				return Mono.error(new InvalidNumericeFound(Constants.INVALID_NUMERICE));
			}
			if (!ObjectUtils.isEmpty(user.getUserId())) {
				updateUser.setUserId(user.getUserId());
			}
			if (!ObjectUtils.isEmpty(user.getFirstName())) {
				updateUser.setFirstName(user.getFirstName());
			}
			if (!ObjectUtils.isEmpty(user.getLastName())) {
				updateUser.setLastName(user.getLastName());
			}
			if (!ObjectUtils.isEmpty(user.getUsername())) {
				updateUser.setUsername(user.getUsername());
			}
			if (!ObjectUtils.isEmpty(user.getEmail())) {
				updateUser.setEmail(user.getEmail());
			}
			if (!ObjectUtils.isEmpty(user.getAccountBalance())) {
				updatedAccount += Double.parseDouble(user.getAccountBalance());
			}
			updateUser.setAccountBalance(updatedAccount.toString());
			return userRepository.save(updateUser).map(updatedUser -> {
				LOGGER.info(LoggerConstants.CREAE_PORTFOLIO_USER);
				orderDetailsRespository.updatePortfolioOrder(updatedUser, userPortfolioResponse.getOrderInfo(),
						userPortfolioResponse.getInvestment(), userPortfolioResponse.getEarning(),
						userPortfolioResponse.getIncome(), userPortfolioResponse.getTotalProfit(),
						userPortfolioResponse.getTotalLoss(), userPortfolioResponse.getOverAllProfit(),
						updatedUser.getAccountBalance());
				userResponse.setFirstName(updatedUser.getFirstName());
				userResponse.setLastName(updatedUser.getLastName());
				userResponse.setEmail(updatedUser.getEmail());
				userResponse.setUserId(updatedUser.getUserId());
				userResponse.setAccountBalance(updatedUser.getAccountBalance());
				userResponse.setUsername(updatedUser.getUsername());
				return userResponse;
			}).doOnSuccess(success -> {
				LOGGER.debug(LoggerConstants.SUCCESS_UPDATE_USER_MSG);
			}).doOnError(err -> LOGGER.error(Constants.ERROR_OCCURING_WHILE_UPDATE_USER));
		}).onErrorResume(err -> Mono.error(new UserNotFound(Constants.USER_NOT_FOUND)));
	}

	/**
	 * User createUser.
	 *
	 * @param user the user
	 * 
	 * @return the mono
	 */
	@Override
	public Mono<UserResponse> createUser(User user, String roles) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.CREATE_USER_METHOD);
		StringBuilder userIdkey = new StringBuilder();
		userIdkey.append(Constants.USER_PREFIX);
		userIdkey.append(user.getUserId());
		return userRepository.existsById(userIdkey.toString())
				.flatMap(isFound -> createIfNotExist(user, isFound, roles)).doOnSuccess(success -> {
					LOGGER.debug(LoggerConstants.SUCCESS_MSG_USER_EXIST);
				}).doOnError(error -> {
					LOGGER.error(LoggerConstants.ERROR_MSG_USER_EXSIT);
				});
	}
}
