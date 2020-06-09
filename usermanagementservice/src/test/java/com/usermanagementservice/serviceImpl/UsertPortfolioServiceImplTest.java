package com.usermanagementservice.serviceImpl;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.subdoc.DocumentFragment;
import com.usermanagementservice.constants.ApiEndpointsConstant;
import com.usermanagementservice.constants.ConstantsTest;
import com.usermanagementservice.model.LatestFeed;
import com.usermanagementservice.model.LiveFeeds;
import com.usermanagementservice.model.OrderDetails;
import com.usermanagementservice.model.OrderInfo;
import com.usermanagementservice.model.OrderParameters;
import com.usermanagementservice.model.User;
import com.usermanagementservice.model.UserPortfolioResponse;
import com.usermanagementservice.model.sell.SellResponse;
import com.usermanagementservice.respository.OrderDetailsRespository;
import com.usermanagementservice.respository.UserRepository;
import com.usermanagementservice.service.OrderHistoryService;
import com.usermanagementservice.service.StockService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The class UsertPortfolioServiceImplTest
 * 
 * @author harsh.jain
 *
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UsertPortfolioServiceImplTest {

	/** The broker. */
	@Value("${broker.base.url}")
	public String broker;

	/** The web test client. */
	@Mock
	public WebTestClient webTestClient;

	/**
	 * The UserRepository.
	 */
	@Mock
	private UserRepository userRepository;

	/**
	 * The OrderDetailsRespository.
	 */
	@Mock
	private OrderDetailsRespository orderDetailsRespository;

	/**
	 * The StockService.
	 */
	@Mock
	private StockService stockService;

	/**
	 * The UserPortfolioServiceImpl.
	 */
	@InjectMocks
	private UserPortfolioServiceImpl userPortfolioServiceImpl;

	/**
	 * The OrderHistoryService.
	 */
	@Mock
	private OrderHistoryService orderHistoryService;

	/**
	 * The init.
	 */
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * The buySameStockOrderTest.
	 */
	@Test
	public void buySameStockOrderTest() {
		Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Mono.just((getUser())));
		OrderHistoryService orderHistoryService = mock(OrderHistoryService.class);
		orderHistoryService.saveUserOrderHistory(Mockito.any());
		Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Mono.just((getUser())));
		Mockito.when(stockService.getlatestStockPrice(Mockito.anyString(), Mockito.anyString()))
				.thenReturn((LiveFeeds()));
		Mockito.when(orderDetailsRespository.getUserPortfolio(Mockito.anyString()))
				.thenReturn((UserTataMotorStockPortfolioResponse()));
		Mono<OrderInfo> newBuyStock = userPortfolioServiceImpl.buyStock(orderDetails());
		StepVerifier.create(newBuyStock).thenConsumeWhile(response -> {
			Assert.assertNotNull(response.getInvestment());
			return true;
		}).verifyComplete();
	}

	/**
	 * The documentFragment.
	 * 
	 * @return DocumentFragment<Mutation
	 * 
	 */
	private DocumentFragment<Mutation> documentFragment() {
		DocumentFragment<Mutation> doc = new DocumentFragment<Mutation>(ConstantsTest.DEFALUT_ID, 0, null, null);
		return doc;
	}

	/**
	 * The buyGetUserPreSellOrderTest.
	 */
	@Test
	public void buyGetUserPreSellOrderTest() {
		Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Mono.just((getUser())));
		Mockito.when(stockService.getlatestStockPrice(Mockito.anyString(), Mockito.anyString()))
				.thenReturn((LiveFeeds()));
		Mockito.when(orderDetailsRespository.getUserPortfolio(Mockito.anyString()))
				.thenReturn((UserTataMotorStockPortfolioResponse()));
		Mono<SellResponse> newBuyStock = userPortfolioServiceImpl.getUserPreSellOrder(ConstantsTest.TEN,
				ConstantsTest.SYMBOLID_TATA, ConstantsTest.TEN, ConstantsTest.TEN);
		StepVerifier.create(newBuyStock).thenConsumeWhile(response -> {
			Assert.assertNotNull(response.getInvestment());
			return true;
		}).verifyComplete();
	}

	/**
	 * The buyNewStockOrderTest.
	 */
	@Test
	public void buyNewStockOrderTest() {
		String url = this.broker + ApiEndpointsConstant.BROKER_GET_CALL_API + ConstantsTest.DAY;
		Mockito.when(userPortfolioServiceImpl.getPortfolioUser(Mockito.any())).thenReturn(userPortfolioResponse());
		webTestClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody()
				.consumeWith(result -> {
					Assertions.assertThat(result.getResponseBody()).isNotNull();
				});
		Mockito.when(orderDetailsRespository.saveOrder(Mockito.any(), Mockito.anyString()))
				.thenReturn(documentFragment());
		OrderHistoryService orderHistoryService = mock(OrderHistoryService.class);
		orderHistoryService.saveUserOrderHistory(Mockito.any());
		Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Mono.just((getUser())));
		Mockito.when(stockService.getlatestStockPrice(Mockito.anyString(), Mockito.anyString()))
				.thenReturn((LiveFeeds()));
		Mockito.when(orderDetailsRespository.getUserPortfolio(Mockito.anyString()))
				.thenReturn((UserVedlStockPortfolioResponse()));
		Mono<OrderInfo> newBuyStock = userPortfolioServiceImpl.buyStock(orderDetails());
		StepVerifier.create(newBuyStock).thenConsumeWhile(response -> {
			Assert.assertNotNull(response.getInvestment());
			return true;
		}).verifyComplete();
	}

	/**
	 * The sellOrderTest.
	 */
	@Test
	public void sellOrderTest() {
		Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Mono.just((getUser())));
		Mockito.when(stockService.getlatestStockPrice(Mockito.anyString(), Mockito.anyString()))
				.thenReturn((LiveFeeds()));
		Mockito.when(orderDetailsRespository.getUserPortfolio(Mockito.anyString()))
				.thenReturn((UserTataMotorStockPortfolioResponse()));
		Mono<SellResponse> newSellStock = userPortfolioServiceImpl.sellStock(ConstantsTest.SYMBOL,
				ConstantsTest.SYMBOLID_TATA, ConstantsTest.FOURTY_ONE, ConstantsTest.QUANTITY_70);
		StepVerifier.create(newSellStock).thenConsumeWhile(response -> {
			Assert.assertNotNull(response.getSymbolName());
			return true;
		}).verifyComplete();
	}

	/**
	 * the UserPortfolioResponse
	 * 
	 * @return UserPortfolioResponse
	 */
	private UserPortfolioResponse UserVedlStockPortfolioResponse() {
		UserPortfolioResponse userPortfolioResponse = new UserPortfolioResponse();
		userPortfolioResponse.setEarning(ConstantsTest.THOUSAND);
		userPortfolioResponse.setInvestment(ConstantsTest.THOUSAND);
		userPortfolioResponse.setIncome(ConstantsTest.THOUSAND);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setBidPrice(ConstantsTest.THOUSAND);
		orderInfo.setBuyPrice(ConstantsTest.THOUSAND);
		orderInfo.setCurrentPrice(ConstantsTest.HUNDRED);
		orderInfo.setInvestment(ConstantsTest.INVESTMENT);
		orderInfo.setEarning(ConstantsTest.HUNDRED);
		orderInfo.setTotalProfit(ConstantsTest.HUNDRED);
		orderInfo.setOrderId(ConstantsTest.ORDER_ID);
		OrderParameters orderParameter = new OrderParameters();
		orderParameter.setSymbol(ConstantsTest.SYMBOLID_VEDL);
		orderParameter.setDuration(ConstantsTest.DAY);
		orderParameter.setQuantity(ConstantsTest.QUANTITY_70);
		orderParameter.setSide(ConstantsTest.BUY);
		orderInfo.setOrderParameters(orderParameter);
		List<OrderInfo> ListOrderInfo = new ArrayList<OrderInfo>();
		ListOrderInfo.add(orderInfo);
		userPortfolioResponse.setOrderInfo(ListOrderInfo);
		return userPortfolioResponse;
	}

	/**
	 * the UserPortfolioResponse
	 * 
	 * @return UserPortfolioResponse
	 */
	private UserPortfolioResponse UserTataMotorStockPortfolioResponse() {
		UserPortfolioResponse userPortfolioResponse = new UserPortfolioResponse();
		userPortfolioResponse.setEarning(ConstantsTest.THOUSAND);
		userPortfolioResponse.setInvestment(ConstantsTest.THOUSAND);
		userPortfolioResponse.setIncome(ConstantsTest.THOUSAND);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setBidPrice(ConstantsTest.THOUSAND);
		orderInfo.setBuyPrice(ConstantsTest.HUNDRED);
		orderInfo.setCurrentPrice(ConstantsTest.HUNDRED);
		orderInfo.setInvestment(ConstantsTest.INVESTMENT);
		orderInfo.setEarning(ConstantsTest.HUNDRED);
		orderInfo.setTotalProfit(ConstantsTest.HUNDRED);
		orderInfo.setOrderId(ConstantsTest.ORDER_ID);
		OrderParameters orderParameter = new OrderParameters();
		orderParameter.setSymbol(ConstantsTest.SYMBOL);
		orderParameter.setDuration(ConstantsTest.DAY);
		orderParameter.setQuantity(ConstantsTest.QUANTITY_70);
		orderParameter.setSide(ConstantsTest.BUY);
		orderInfo.setOrderParameters(orderParameter);
		List<OrderInfo> ListOrderInfo = new ArrayList<OrderInfo>();
		ListOrderInfo.add(orderInfo);
		userPortfolioResponse.setOrderInfo(ListOrderInfo);
		return userPortfolioResponse;
	}

	/**
	 * the LiveFeeds
	 * 
	 * @return Mono.
	 */
	private Mono<LiveFeeds> LiveFeeds() {
		LiveFeeds liveFeeds = new LiveFeeds();
		liveFeeds.setSymbol(ConstantsTest.SYMBOL);
		liveFeeds.setSymbolId(ConstantsTest.SYMBOLID_TATA);
		LatestFeed latestFeed = new LatestFeed();
		latestFeed.setCurrentPrice(ConstantsTest.HUNDRED);
		latestFeed.setQuantity(ConstantsTest.QUANTITY_100);
		liveFeeds.setLatestFeed(latestFeed);
		return Mono.just(liveFeeds);
	}

	/**
	 * the getUser
	 * 
	 * @return User.
	 */
	private User getUser() {
		User user = new User();
		user.setUserId(ConstantsTest.USER_ID);
		user.setUsername(ConstantsTest.USER_NAME);
		user.setLastName(ConstantsTest.LAST_NAME);
		user.setAccountBalance(ConstantsTest.ACCOUNT_BALANCE);
		return user;
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
	 * the orderDetails
	 * 
	 * @return User.
	 */
	private OrderDetails orderDetails() {
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setUserId(ConstantsTest.USER_ID);
		orderDetails.setSymbol(ConstantsTest.SYMBOL);
		orderDetails.setSymbolId(ConstantsTest.SYMBOLID_TATA);
		orderDetails.setDuration(ConstantsTest.DAY);
		orderDetails.setCurretnAmount(ConstantsTest.THOUSAND);
		orderDetails.setQuantity(ConstantsTest.QUANTITY_70);
		orderDetails.setBidPriceFlag(Boolean.FALSE);
		orderDetails.setPrice(ConstantsTest.EMPTY);
		return orderDetails;
	}
}
