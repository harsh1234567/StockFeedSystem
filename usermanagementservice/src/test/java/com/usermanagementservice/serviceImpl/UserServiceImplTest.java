package com.usermanagementservice.serviceImpl;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.usermanagementservice.authsecurity.CustumPasswordEncoder;
import com.usermanagementservice.constants.ConstantsTest;
import com.usermanagementservice.model.OrderInfo;
import com.usermanagementservice.model.OrderParameters;
import com.usermanagementservice.model.User;
import com.usermanagementservice.model.UserPortfolio;
import com.usermanagementservice.model.UserPortfolioResponse;
import com.usermanagementservice.model.response.UserResponse;
import com.usermanagementservice.respository.OrderDetailsRespository;
import com.usermanagementservice.respository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The class UserServiceImplTest.
 * 
 * @author harsh.jain
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	/** The userRepo. */
	@Mock
	private UserRepository userRepository;

	/** The orderDetailsRespository. */
	@Mock
	private OrderDetailsRespository orderDetailsRespository;

	/** The UserServiceImpl */
	@InjectMocks
	private UserServiceImpl userServiceImpl;

	/** The UserPortfolioServiceImpl */
	@Mock
	private UserPortfolioServiceImpl userPortfolioServiceImpl;

	/** The passwordEncoder. */
	@Mock
	private CustumPasswordEncoder passwordEncoder;

	/**
	 * 
	 * The createIfUserNotExistTest
	 * 
	 */
	@Test
	public void createIfUserNotExistTest() {
		CustumPasswordEncoder passwordEncoder = mock(CustumPasswordEncoder.class);
		passwordEncoder.encode(Mockito.anyString());
		Mockito.when(userRepository.existsById(Mockito.anyString())).thenReturn(Mono.just((Boolean.FALSE)));
		Mockito.when(userRepository.save(Mockito.any())).thenReturn(Mono.just(getUser()));
		Mono<UserResponse> newCreateUser = userServiceImpl.createUser(getUser(), ConstantsTest.ADMIN);
		StepVerifier.create(newCreateUser).thenConsumeWhile(response -> {
			Assert.assertNotNull(response.getUserId());
			return true;
		}).verifyComplete();
	}

	/**
	 * 
	 * The getAllUserPortfolioTest
	 * 
	 */
	@Test
	public void getAllUserPortfolioTest() {
		Mockito.when(orderDetailsRespository.findByIdAllPortfolio(Mockito.anyString()))
				.thenReturn(Flux.just((getUserPortfolio())));
		Flux<UserPortfolioResponse> newCreateUser = userServiceImpl.getAllUserPortfolio(ConstantsTest.TEN);
		StepVerifier.create(newCreateUser).thenConsumeWhile(response -> {
			Assert.assertNotNull(response.getEarning());
			return true;
		}).verifyComplete();
	}

	/**
	 * 
	 * The getUserPortfolioTest
	 * 
	 */
	@Test
	public void getUserPortfolioTest() {
		Mockito.when(orderDetailsRespository.findByIdPortfolio(Mockito.anyString()))
				.thenReturn(Mono.just((userPortfolio())));
		Mono<List<OrderInfo>> newCreateUser = userServiceImpl.getPortfolio(ConstantsTest.FOURTY_ONE,
				ConstantsTest.USER_NAME);
		StepVerifier.create(newCreateUser).assertNext(response -> {
			response.forEach(res -> {
				Assert.assertNotNull(res.getEarning());
			});
		}).verifyComplete();
	}

	/**
	 * 
	 * The getUserPortfolio
	 * 
	 * @return UserPortfolio .
	 * 
	 */
	private UserPortfolio getUserPortfolio() {
		UserPortfolio userPortfolio = new UserPortfolio();
		userPortfolio.setEarning(ConstantsTest.TWENTY_ONE_HUNDRED);
		userPortfolio.setAccountBalance(ConstantsTest.THOUSAND);
		List<OrderInfo> listOrderInfo = new ArrayList<OrderInfo>();
		userPortfolio.setListOrderInfo(listOrderInfo);
		return userPortfolio;
	}

	/**
	 * 
	 * The validateUser
	 * 
	 */
	@Test
	public void validateUserTest() {
		Mono<UserResponse> newCreateUser = userServiceImpl.validateUser(getUser());
		StepVerifier.create(newCreateUser).thenConsumeWhile(response -> {
			Assert.assertNull(response.getUserId());
			return true;
		}).verifyComplete();
	}

	/**
	 * 
	 * The validateUser
	 * 
	 */
	@Test
	public void updateUserTest() {
		Mockito.when(userPortfolioServiceImpl.getPortfolioUser(Mockito.any())).thenReturn(userPortfolioResponse());
		Mockito.when(userRepository.existsById(Mockito.anyString())).thenReturn(Mono.just((Boolean.TRUE)));
		Mockito.when(userRepository.findByuserId(Mockito.anyString())).thenReturn(Mono.just((getUser())));

		Mockito.when(userRepository.save(Mockito.any())).thenReturn(Mono.just(getUser()));
		Mono<UserResponse> newCreateUser = userServiceImpl.updateUser(getUser(), ConstantsTest.FOURTY_ONE);
		StepVerifier.create(newCreateUser).thenConsumeWhile(response -> {
			Assert.assertNotNull(response.getUserId());
			return true;
		}).verifyComplete();
	}

	/**
	 * 
	 * The UserPortfolioResponse
	 * 
	 * @return UserPortfolioResponse;
	 * 
	 */
	private UserPortfolioResponse userPortfolioResponse() {
		UserPortfolioResponse userPortfolioResponse = new UserPortfolioResponse();
		userPortfolioResponse.setAccountBalance(ConstantsTest.THOUSAND);
		userPortfolioResponse.setEarning(ConstantsTest.TWENTY_ONE_HUNDRED);
		List<OrderInfo> listOrderInfo = new ArrayList<OrderInfo>();
		userPortfolioResponse.setOrderInfo(listOrderInfo);
		return userPortfolioResponse;
	}

	/**
	 * 
	 * The getUserData
	 * 
	 */
	@Test
	public void getUserDataTest() {
		Mockito.when(userRepository.findByuserId(Mockito.anyString())).thenReturn(Mono.just((getUser())));
		Mono<User> newCreateUser = userServiceImpl.getUser(ConstantsTest.TEN);
		StepVerifier.create(newCreateUser).thenConsumeWhile(response -> {
			Assert.assertNotNull(response.getUserId());
			return true;
		}).verifyComplete();
	}

	/**
	 * The getPortfolioByUserTest
	 * 
	 */
	@Test
	public void getPortfolioByUserTest() {
		Mockito.when(orderDetailsRespository.findByIdPortfolio(Mockito.anyString()))
				.thenReturn(Mono.just((userPortfolio())));
		Mono<UserPortfolioResponse> newCreateUser = userServiceImpl.getUserPortfolio(ConstantsTest.FOURTY_ONE,
				ConstantsTest.USER_NAME);
		StepVerifier.create(newCreateUser).thenConsumeWhile(response -> {
			response.getOrderInfo().forEach(orders -> {
				Assert.assertNotNull(orders.getEarning());
			});
			return true;
		}).verifyComplete();
	}

	/**
	 * The UserPortfolio userPortfolio
	 * 
	 */
	private UserPortfolio userPortfolio() {
		UserPortfolio userPortfolio = new UserPortfolio();
		userPortfolio.setEarning(ConstantsTest.TWENTY_ONE_HUNDRED);
		userPortfolio.setInvestment(ConstantsTest.TWENTY_ONE_HUNDRED);
		userPortfolio.setIncome(ConstantsTest.TWENTY_ONE_HUNDRED);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setBidPrice(ConstantsTest.TWENTY_ONE_HUNDRED);
		orderInfo.setCurrentPrice(ConstantsTest.FOURTY_ONE);
		orderInfo.setInvestment(ConstantsTest.TWENTY_ONE_HUNDRED);
		orderInfo.setEarning(ConstantsTest.TWENTY_ONE_HUNDRED);
		orderInfo.setOrderId(ConstantsTest.ORDER_ID);
		OrderParameters orderParameter = new OrderParameters();
		orderParameter.setSymbol(ConstantsTest.SYMBOL);
		orderParameter.setDuration(ConstantsTest.DAY);
		orderParameter.setQuantity(ConstantsTest.FOURTY_ONE);
		orderParameter.setSide(ConstantsTest.BUY);
		orderInfo.setOrderParameters(orderParameter);
		List<OrderInfo> ListOrderInfo = new ArrayList<OrderInfo>();
		ListOrderInfo.add(orderInfo);
		userPortfolio.setListOrderInfo(ListOrderInfo);
		return userPortfolio;
	}

	/**
	 * 
	 * The getUser
	 * 
	 * @return User
	 * 
	 */
	private User getUser() {
		User user = new User();
		user.setUserId(ConstantsTest.FOURTY_ONE);
		user.setUsername(ConstantsTest.USER_NAME);
		user.setLastName(ConstantsTest.LAST_NAME);
		user.setPassword(ConstantsTest.PASSWORD);
		return user;
	}
}
