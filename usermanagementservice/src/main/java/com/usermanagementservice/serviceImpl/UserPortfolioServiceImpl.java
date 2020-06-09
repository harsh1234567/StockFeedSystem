package com.usermanagementservice.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.subdoc.DocumentFragment;
import com.usermanagementservice.constants.ApiEndpointsConstant;
import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.enums.OrderStatus;
import com.usermanagementservice.exception.AccountBalanceException;
import com.usermanagementservice.exception.AvailableQuantityNotFound;
import com.usermanagementservice.exception.BrokerNotMatching;
import com.usermanagementservice.exception.BrokerServiceNotFound;
import com.usermanagementservice.exception.NotValidQuantity;
import com.usermanagementservice.exception.SellOrderNotFound;
import com.usermanagementservice.exception.SymbolNotFound;
import com.usermanagementservice.exception.UserNotFound;
import com.usermanagementservice.model.LiveFeeds;
import com.usermanagementservice.model.OrderDetails;
import com.usermanagementservice.model.OrderHistory;
import com.usermanagementservice.model.OrderInfo;
import com.usermanagementservice.model.OrderParameters;
import com.usermanagementservice.model.OrderState;
import com.usermanagementservice.model.User;
import com.usermanagementservice.model.UserPortfolio;
import com.usermanagementservice.model.UserPortfolioResponse;
import com.usermanagementservice.model.broker.Broker;
import com.usermanagementservice.model.sell.SellResponse;
import com.usermanagementservice.respository.OrderDetailsRespository;
import com.usermanagementservice.respository.UserRepository;
import com.usermanagementservice.service.OrderHistoryService;
import com.usermanagementservice.service.StockService;
import com.usermanagementservice.service.UserPortfolioService;
import com.usermanagementservice.utils.CommonUtils;
import com.usermanagementservice.utils.DateUtils;

import reactor.core.publisher.Mono;

/**
 * The class UserPortfolioServiceImpl.
 * 
 * @author harsh.jain
 *
 */
@Service
public class UserPortfolioServiceImpl implements UserPortfolioService {

	/**
	 * The orderHistoryService.
	 */
	@Autowired
	private OrderHistoryService orderHistoryService;

	/**
	 * The UserRepository
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * The OrderDetailsRespository
	 */
	@Autowired
	private OrderDetailsRespository orderDetailsRespository;

	/** The web test client. */
	@Autowired
	public WebClient webClient;

	/**
	 * The StockService
	 */
	@Autowired
	private StockService stockService;

	/** The LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(UserPortfolioServiceImpl.class);

	@Value("${broker.base.url}")
	private String BASE_URL;

	/**
	 * buyStock methods
	 * 
	 * @param orderDetails
	 * 
	 * @return OrderInfo.
	 * 
	 */
	@Override
	public Mono<OrderInfo> buyStock(OrderDetails orderDetails) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.BUY_STOCK);
		return buyOrder(orderDetails);
	}

	/**
	 * sellStock methods
	 * 
	 * @param symbolName.
	 * 
	 * @param symbolId.
	 * 
	 * @param userId.
	 * 
	 * @return Mono.
	 * 
	 */
	@Override
	public Mono<SellResponse> sellStock(String symbolName, String symbolId, String userId, String quantity) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.SELL_STOCK);
		return sellOrder(symbolName, symbolId, userId, quantity);
	}

	/**
	 * brokerCall method
	 * 
	 * @param String duration
	 * 
	 * @return Broker.
	 * 
	 */
	@Override
	public Mono<Broker> brokerCall(String duration) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.BROKER_CALL);
		return webClient.get().uri(BASE_URL + ApiEndpointsConstant.BROKER_GET_CALL_API + duration).retrieve()
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> {
					Mono<String> errorMono = clientResponse.bodyToMono(String.class);
					return errorMono.flatMap(errorMessage -> {
						return Mono.error(new BrokerServiceNotFound(Constants.BROKER_NOT_FOUND));
					});
				}).bodyToMono(Broker.class)
				.onErrorResume(error -> Mono.error(new BrokerServiceNotFound(Constants.BROKER_NOT_FOUND)));
	}

	/**
	 * The prepareingOrder
	 * 
	 * @param orderDetails orderDetails
	 * 
	 * @return Mono.
	 * 
	 */
	private Mono<OrderInfo> buyOrder(OrderDetails orderDetails) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.PREPARING_ORDER);
		Mono<User> user = getUser(orderDetails.getUserId());
		return user.flatMap(u -> {
			return buyCalculation(orderDetails, u);
		}).switchIfEmpty(Mono.error(new UserNotFound(Constants.USER_NOT_FOUND)));
	}

	/**
	 * The getUser
	 * 
	 * @param String userId.
	 * 
	 * @return Mono.
	 *
	 */
	private Mono<User> getUser(String userId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER);
		StringBuilder getId = new StringBuilder(Constants.USER_PREFIX);
		getId.append(userId);
		return userRepository.findById(getId.toString()).doOnSuccess(success -> {
			LOGGER.info(LoggerConstants.GET_USER_SUCCESSFULLY, success);
		}).doOnError(error -> {
			LOGGER.error(LoggerConstants.GET_USER_ERROR, error);
		});
	}

	/**
	 * The beforeBuyCalculation
	 * 
	 * @param OrderDetails orderDetails
	 * 
	 * @param User         user.
	 * 
	 * @return Mono.
	 * 
	 */
	private Mono<OrderInfo> buyCalculation(OrderDetails orderDetails, User user) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.BEFORE_BUY_CALCULATION);
		Mono<LiveFeeds> liveFeeds = GetCurrentMarketData(orderDetails.getSymbol(), orderDetails.getSymbolId());
		return liveFeeds.flatMap(stocks -> {
			if (Double.parseDouble(stocks.getLatestFeed().getQuantity()) < Double
					.parseDouble(orderDetails.getQuantity())) {
				return Mono.error(new AvailableQuantityNotFound(Constants.AVAILABLE_QUANTITY_NOT_FOUND));
			}
			OrderInfo orderInfo = new OrderInfo();
			OrderState orderState = new OrderState();
			OrderParameters orderParameters = new OrderParameters();
			orderParameters.setSide(Constants.BUY);
			orderInfo.setPlaceTime(DateUtils.getTimeStamp());
			orderParameters.setSymbolId(stocks.getSymbolId());
			return brokerCall(orderDetails.getDuration()).flatMap((broker) -> {
				LOGGER.debug(LoggerConstants.INSIDE_BROKER_CALL);
				return checkExistStock(user, stocks, orderDetails, broker).flatMap(checkorderInfo -> {
					LOGGER.debug(LoggerConstants.INSIDE_CHECK_EXIST_STOCK_CALL);
					if (checkorderInfo.getEarning() != null) {
						return Mono.just(checkorderInfo);
					}
					if (stocks.getSymbolId().equals(orderDetails.getSymbolId())) {
						orderState.setLastUpdate(DateUtils.getTimeStamp());
					}
					if (orderDetails.getBidPriceFlag()) {
						orderState.setStatus(OrderStatus.PENDING.getVal());
						orderState.setLastUpdate(DateUtils.getTimeStamp());
						orderInfo.setBidPrice(orderDetails.getBidPrice());
						orderInfo.setCurrentPrice(Constants.ZERO_DOUBLE_VALUE);
						orderParameters.setQuantity(orderDetails.getQuantity());
						orderInfo.setOrderState(orderState);
					} else if (!orderDetails.getBidPriceFlag() && !orderDetails.getPrice().isEmpty()) {
						orderState.setStatus(OrderStatus.BUYING.getVal());
						orderState.setLastUpdate(DateUtils.getTimeStamp());
						orderInfo.setCurrentPrice(orderDetails.getPrice());
						orderInfo.setOrderState(orderState);
					} else if (!orderDetails.getBidPriceFlag()
							&& (!orderDetails.getPrice().isEmpty() || !orderDetails.getQuantity().isEmpty())) {
						LOGGER.debug(LoggerConstants.GET_CURRENT_PRICE_STOCK);
						orderInfo.setBroker(broker);
						orderState.setStatus(OrderStatus.BUYING.getVal());
						orderParameters.setQuantity(orderDetails.getQuantity());
						orderState.setLastUpdate(DateUtils.getTimeStamp());
						orderInfo.setCurrentPrice(stocks.getLatestFeed().getCurrentPrice());
						Double brokerageCharge = Double.parseDouble(broker.getBrokerCharges())
								* Double.parseDouble(orderDetails.getQuantity());
						Double investment = Double.parseDouble(stocks.getLatestFeed().getCurrentPrice())
								* Double.parseDouble(orderDetails.getQuantity());
						orderInfo.setInvestment(investment.toString());
						Double earning = -brokerageCharge;
						Double income = investment + earning;
						orderInfo.setIncome(income.toString());
						orderInfo.setEarning(earning.toString());
						orderInfo.setBuyPrice(stocks.getLatestFeed().getCurrentPrice());
						orderInfo.setBidPrice(Constants.ZERO_DOUBLE_VALUE);
						orderInfo.setOrderState(orderState);
					}
					orderParameters.setSymbol(stocks.getSymbol());
					orderParameters.setDuration(orderDetails.getDuration());
					orderInfo.setOrderParameters(orderParameters);
					orderInfo.setUserId(user.getId());
					StringBuilder orderKeyId = new StringBuilder();
					orderKeyId.append(Constants.ORDER_PREFIX);
					orderKeyId.append(stocks.getSymbol());
					orderKeyId.append(Constants.COLON_COLON);
					orderKeyId.append(stocks.getSymbolId());
					orderKeyId.append(Constants.COLON_COLON);
					orderKeyId.append(user.getUserId());
					orderInfo.setOrderId(orderKeyId.toString());
					return saveOrder(orderInfo, user);
				});
			}).switchIfEmpty(Mono.error(new BrokerNotMatching(Constants.BROKER_DETAILS_NOT_FOUND)));
		}).doOnError(error -> LOGGER.error(LoggerConstants.ERROR_OCCURED_WHILE_STOCK_FETCHING));
	}

	/**
	 * The checkExistStock
	 * 
	 * @param User         user.
	 * 
	 * @param LiveFeeds    liveFeeds
	 * 
	 * @param OrderDetails orderDetails
	 * 
	 * @return OrderInfo.
	 * 
	 */
	private Mono<OrderInfo> checkExistStock(User user, LiveFeeds liveFeeds, OrderDetails orderDetails, Broker broker) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.CHECK_EXIST_STOCK);
		if (Double.parseDouble(liveFeeds.getLatestFeed().getQuantity()) < Double
				.parseDouble(orderDetails.getQuantity())) {
			LOGGER.error(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_QUANTITY_IS_LESS);
			throw new AvailableQuantityNotFound(Constants.AVAILABLE_QUANTITY_NOT_FOUND);
		}
		UserPortfolioResponse userPortfolioResponse = getPortfolioUser(CommonUtils.portfolioUserId(user));
		List<OrderInfo> listOrderInfo = CommonUtils.collectionToStream(userPortfolioResponse.getOrderInfo())
				.map(order -> {
					LOGGER.debug(LoggerConstants.METHOD_INVOCATION,
							LoggerConstants.ORDER_INFO_DETAIL_WITH_ID + order.getOrderId());
					if (order.getOrderParameters().getSymbol().equals(liveFeeds.getSymbol())) {
						StringBuilder orderKeyId = new StringBuilder();
						orderKeyId.append(Constants.ORDER_PREFIX);
						orderKeyId.append(liveFeeds.getSymbol());
						orderKeyId.append(Constants.COLON_COLON);
						orderKeyId.append(liveFeeds.getSymbolId());
						orderKeyId.append(Constants.COLON_COLON);
						orderKeyId.append(user.getUserId());
						order.setOrderId(orderKeyId.toString());
						order.setBroker(broker);
						order.setCurrentPrice(liveFeeds.getLatestFeed().getCurrentPrice());
						Double brokerageCharge = Double.parseDouble(broker.getBrokerCharges())
								* Double.parseDouble(orderDetails.getQuantity());
						Double newInvestment = Double.parseDouble(liveFeeds.getLatestFeed().getCurrentPrice())
								* Double.parseDouble(orderDetails.getQuantity());
						Double totalInvestment = Double.parseDouble(order.getInvestment()) + newInvestment;
						order.setInvestment(totalInvestment.toString());
						Double earning = Double.parseDouble(order.getEarning()) - brokerageCharge;
						order.setEarning(earning.toString());
						Double income = totalInvestment + earning;
						order.setIncome(income.toString());
						if (earning < 0.0) {
							order.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
							order.setTotalLoss(earning.toString());
						} else {
							order.setTotalProfit(earning.toString());
							order.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
						}
						String quantity = String.valueOf(Integer.parseInt(order.getOrderParameters().getQuantity())
								+ Integer.parseInt(orderDetails.getQuantity()));
						order.getOrderParameters().setQuantity(quantity);
						Double avgBuyPrice = totalInvestment / Double.parseDouble(quantity);
						order.setBuyPrice(avgBuyPrice.toString());
					}
					return order;
				}).collect(Collectors.toList());
		Double earning = listOrderInfo.parallelStream().reduce(0.0,
				(stockEarning, orderList) -> stockEarning + Double.parseDouble(orderList.getEarning()), Double::sum);
		Double invest = listOrderInfo.parallelStream().reduce(0.0,
				(investOnStock, orderList) -> investOnStock + Double.parseDouble(orderList.getInvestment()),
				Double::sum);
		Double income = listOrderInfo.parallelStream().reduce(0.0,
				(incomeOnStock, orderList) -> incomeOnStock + Double.parseDouble(orderList.getIncome()), Double::sum);
		Double profit = listOrderInfo.parallelStream().reduce(0.0,
				(pro, orderList) -> pro + Double.parseDouble(orderList.getTotalProfit()), Double::sum);

		Double loss = listOrderInfo.parallelStream().reduce(0.0,
				(lo, orderList) -> lo + Double.parseDouble(orderList.getTotalLoss()), Double::sum);
		Double eligibleInvestment = Double.parseDouble(orderDetails.getQuantity())
				* (Double.parseDouble(liveFeeds.getLatestFeed().getCurrentPrice())
						+ Double.parseDouble(broker.getBrokerCharges()));
		if (eligibleInvestment > Double.parseDouble(userPortfolioResponse.getAccountBalance())) {
			throw new AccountBalanceException(Constants.YOUR_CURRENT_BALANCE_IS_LOW);
		}
		return brokerCall(orderDetails.getDuration()).flatMap(bro -> {
			Double updatedAmount = 0.0;
			if (!listOrderInfo.isEmpty()) {

				updatedAmount = Double.parseDouble(user.getAccountBalance())
						- Double.parseDouble(liveFeeds.getLatestFeed().getCurrentPrice())
								* Double.parseDouble(orderDetails.getQuantity())
						- Double.parseDouble(bro.getBrokerCharges()) * Double.parseDouble(orderDetails.getQuantity());

				user.setAccountBalance(updatedAmount.toString());
			}
			User updatedUser = getUpdatedUser(user);
			Mono<User> u = userRepository.save(updatedUser);
			return u.flatMap(us -> {
				OrderInfo orderInfo = null;
				try {
					Double overAllProfit = Double.parseDouble(userPortfolioResponse.getOverAllProfit());
					if (!listOrderInfo.isEmpty()) {
						overAllProfit -= Double.parseDouble(orderDetails.getQuantity())
								* (Double.parseDouble(broker.getBrokerCharges()));

						orderInfo = listOrderInfo.stream()
								.filter(order -> order.getOrderParameters().getSymbol().equals(liveFeeds.getSymbol()))
								.findAny().get();
						orderDetailsRespository.updatePortfolioOrder(user, listOrderInfo, invest.toString(),
								earning.toString(), income.toString(), profit.toString(), loss.toString(),
								overAllProfit.toString(), us.getAccountBalance().toString());
						try {
							saveHistoryUser(orderInfo, orderInfo.getOrderParameters().getQuantity(), us,
									OrderStatus.BUYING);
							Thread.sleep(10);
							updateStockQuantity(liveFeeds.getSymbolId(), liveFeeds.getSymbol(),
									orderDetails.getQuantity(), orderDetails.getStatus());
						} catch (Exception e) {
							LOGGER.error(LoggerConstants.ERROR_OCCURED_WHILE_SAVING_ORDER_HISTORY + e);
						}
					}
					LOGGER.info(LoggerConstants.USER_PORTFOLIO + userPortfolioResponse.getEarning());
				} catch (Exception e) {
					LOGGER.error(Constants.ERROR_WHILE_UPDATE_PORTFOLIO);
				}
				if (orderInfo == null) {
					OrderInfo orderIn = new OrderInfo();
					return Mono.just(orderIn);
				}
				return Mono.just(orderInfo);
			});
		});
	}

	/**
	 * The deleteOrderInfoObject
	 * 
	 * @param User   user.
	 * 
	 * @param String symbol.
	 * 
	 * @param String symbolId
	 * 
	 * @return Mono.
	 * 
	 */
	private Mono<SellResponse> deleteOrderInfoObject(User user, String symbol, String symbolId, String quantityField) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.DELETE_ORDER_INFO_OBJECT);
		Mono<LiveFeeds> liveFeeds = GetCurrentMarketData(symbol, symbolId);
		return liveFeeds.flatMap(stocks -> {
			UserPortfolioResponse userPortfolioResponse = getPortfolioUser(CommonUtils.portfolioUserId(user));
			return checkSellOrderExist(user, stocks, userPortfolioResponse).flatMap(orderSellInfo -> {
				if (orderSellInfo != null) {
					return brokerCall(orderSellInfo.getOrderParameters().getDuration()).flatMap(broker -> {
						Double overAllProfit = 0.0;
						Double tProfit = 0.0;
						final Double brokerageCharge = Double.parseDouble(broker.getBrokerCharges())
								* Double.parseDouble(quantityField);
						SellResponse sellResponse = new SellResponse();
						sellResponse.setBuyPrice(orderSellInfo.getBuyPrice());
						sellResponse.setOrderId(orderSellInfo.getOrderId());
						sellResponse.setSymbolName(orderSellInfo.getOrderParameters().getSymbol());
						sellResponse.setSellPrice(stocks.getLatestFeed().getCurrentPrice());
						sellResponse.setStatus(OrderStatus.SELL.getVal().toString());
						Double earning = 0.0;
						Double investment = 0.0;
						List<OrderInfo> listOrderInfo = null;
						Double income = 0.0;
						Double totProfit = 0.0;
						Double profit = 0.0;
						Double loss = 0.0;
						if (orderSellInfo.getOrderParameters().getQuantity().equals(quantityField)) {
							sellResponse.setQuantity(quantityField);
							Double totalInvestment = Double.parseDouble(orderSellInfo.getInvestment());
							sellResponse.setInvestment(totalInvestment.toString());
							tProfit = (Double.parseDouble(stocks.getLatestFeed().getCurrentPrice())
									- Double.parseDouble(orderSellInfo.getBuyPrice()))
									* (Double.parseDouble(orderSellInfo.getOrderParameters().getQuantity()))
									- brokerageCharge;
							earning = Double.parseDouble(userPortfolioResponse.getEarning()) - tProfit;
							if (tProfit < 0) {
								sellResponse.setTotalLoss(tProfit.toString());
								sellResponse.setTotalprofit(Constants.ZERO_DOUBLE_VALUE);
								orderSellInfo.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
								orderSellInfo.setTotalLoss(tProfit.toString());
							} else {
								profit = tProfit;
								sellResponse.setTotalprofit(tProfit.toString());
								sellResponse.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
								orderSellInfo.setTotalProfit(tProfit.toString());
								orderSellInfo.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
							}
							listOrderInfo = CommonUtils.collectionToStream(userPortfolioResponse.getOrderInfo())
									.filter(orderList -> !(orderList.getOrderId().equals(CommonUtils
											.OrderKey(stocks.getSymbol(), stocks.getSymbolId(), user.getUserId()))))
									.collect(Collectors.toList());
							investment = Double.parseDouble(userPortfolioResponse.getInvestment())
									- Double.parseDouble(sellResponse.getInvestment());
							if (listOrderInfo.size() == 0) {
								earning = 0.0;
							}
							income = investment + earning;
							Double totalVal = Double.parseDouble(sellResponse.getInvestment()) + tProfit;
							sellResponse.setTotalValue(totalVal.toString());
						} else if (Integer.parseInt(orderSellInfo.getOrderParameters().getQuantity()) > Integer
								.parseInt((quantityField))) {
							sellResponse.setQuantity(quantityField);
							Double sellInvestment = Double.parseDouble(orderSellInfo.getBuyPrice())
									* (Double.parseDouble(quantityField));
							sellResponse.setInvestment(sellInvestment.toString());
							totProfit = (Double.parseDouble(stocks.getLatestFeed().getCurrentPrice())
									- Double.parseDouble(orderSellInfo.getBuyPrice()))
									* (Double.parseDouble(quantityField)) - 2 * brokerageCharge;
							Double totalValue = sellInvestment + totProfit;
							if (totProfit < 0) {
								sellResponse.setTotalLoss(totProfit.toString());
								sellResponse.setTotalprofit(Constants.ZERO_DOUBLE_VALUE);
							} else {
								sellResponse.setTotalprofit(totProfit.toString());
								sellResponse.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
							}
							sellResponse.setTotalValue(totalValue.toString());
							listOrderInfo = CommonUtils.collectionToStream(userPortfolioResponse.getOrderInfo())
									.map((sellOrder) -> {
										if (sellOrder.getOrderParameters().getSymbol().equals(stocks.getSymbol())) {
											Integer remaningQuantity = Integer
													.parseInt(sellOrder.getOrderParameters().getQuantity())
													- Integer.parseInt(quantityField);
											sellOrder.getOrderParameters().setQuantity(remaningQuantity.toString());
											Double remainingInvestment = Double.parseDouble(sellOrder.getInvestment())
													- (Double.parseDouble(sellOrder.getBuyPrice()))
															* (Double.parseDouble(quantityField));
											sellOrder.setInvestment(remainingInvestment.toString());
											Double totalProfit = (Double
													.parseDouble(stocks.getLatestFeed().getCurrentPrice())
													- Double.parseDouble(sellOrder.getBuyPrice()))
													* (Double.parseDouble(quantityField)) - brokerageCharge;

											Double remainingEarning = Double.parseDouble(sellOrder.getEarning())
													- totalProfit;
											totalProfit = remainingEarning;
											if (totalProfit < 0) {
												sellOrder.setTotalLoss(totalProfit.toString());
												sellOrder.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
											} else {
												sellOrder.setTotalProfit(totalProfit.toString());
												sellOrder.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
											}
											sellOrder.setEarning(remainingEarning.toString());
											Double remainingIncome = remainingInvestment + remainingEarning;
											sellOrder.setIncome(remainingIncome.toString());
										}
										return sellOrder;
									}).collect(Collectors.toList());
						} else {
							return Mono.error(new AvailableQuantityNotFound(Constants.AVAILABLE_QUANTITY_NOT_FOUND));
						}
						earning = listOrderInfo.parallelStream().reduce(0.0,
								(stockEarning, orderList) -> stockEarning + Double.parseDouble(orderList.getEarning()),
								Double::sum);
						investment = listOrderInfo.parallelStream().reduce(0.0, (investOnStock,
								orderList) -> investOnStock + Double.parseDouble(orderList.getInvestment()),
								Double::sum);
						income = listOrderInfo.parallelStream().reduce(0.0,
								(incomeOnStock, orderList) -> incomeOnStock + Double.parseDouble(orderList.getIncome()),
								Double::sum);
						profit = listOrderInfo.parallelStream().reduce(0.0, (profitOnStock, orderList) -> profitOnStock
								+ Double.parseDouble(orderList.getTotalProfit()), Double::sum);
						loss = listOrderInfo.parallelStream().reduce(0.0,
								(lossOnStock, orderList) -> lossOnStock + Double.parseDouble(orderList.getTotalLoss()),
								Double::sum);
						profit = profit + loss;
						if (profit > 0.0) {
							profit = profit + loss;
							loss = 0.0;
						} else {
							profit = 0.0;
							loss = profit + loss;
						}
						overAllProfit = Double.parseDouble(userPortfolioResponse.getOverAllProfit());
						if (tProfit > 0.0) {
							overAllProfit += tProfit;
						} else if (tProfit < 0.0) {
							overAllProfit += tProfit - brokerageCharge;
						} else {
							overAllProfit += Double.parseDouble(userPortfolioResponse.getTotalProfit()) - profit
									+ Double.parseDouble(userPortfolioResponse.getTotalLoss()) - loss;
						}
						Double updatedAmount = Double.parseDouble(user.getAccountBalance())
								+ Double.parseDouble(sellResponse.getTotalValue()) + brokerageCharge;
						user.setAccountBalance(updatedAmount.toString());
						User updatedUser = getUpdatedUser(user);
						orderDetailsRespository.updatePortfolioOrder(user, listOrderInfo, investment.toString(),
								earning.toString(), income.toString(), profit.toString(), loss.toString(),
								overAllProfit.toString(), updatedAmount.toString());
						Mono<User> u = userRepository.save(updatedUser);
						return u.flatMap(us -> {
							try {
								updateStockQuantity(symbolId, symbol, quantityField, OrderStatus.SELL.getVal());
								Thread.sleep(10);
								saveHistoryUser(orderSellInfo, sellResponse.getQuantity(), us, OrderStatus.SELL);
							} catch (Exception e) {
								LOGGER.error(LoggerConstants.ERROR_OCCURED_WHILE_SAVING_ORDER_HISTORY + e);
							}
							return Mono.just(sellResponse);
						});
					});
				}
				return Mono.error(new SellOrderNotFound(Constants.SELL_ORDER_NOT_FOUND));
			}).switchIfEmpty(Mono.error(new SellOrderNotFound(Constants.SELL_ORDER_NOT_FOUND)));
		}).switchIfEmpty(Mono.error(new SymbolNotFound(Constants.INVALID_STOCK)));
	}

	/**
	 * The checkSellOrderExist
	 * 
	 * @param User                  user.
	 * 
	 * @param LiveFeeds             liveFeeds.
	 * 
	 * @param UserPortfolioResponse userPortfolioResponse
	 * 
	 * @return OrderInfo.
	 * 
	 */
	private Mono<OrderInfo> checkSellOrderExist(User user, LiveFeeds liveFeeds,
			UserPortfolioResponse userPortfolioResponse) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.CHECK_SELL_ORDER_EXIST);
		OrderInfo orderSellInfo = null;
		try {
			orderSellInfo = CommonUtils.collectionToStream(userPortfolioResponse.getOrderInfo())
					.filter(orderList -> (orderList.getOrderId().equals(
							CommonUtils.OrderKey(liveFeeds.getSymbol(), liveFeeds.getSymbolId(), user.getUserId()))))
					.findAny().map(x -> x).orElse(null);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR_OCCURING_SELL + e);
		}
		if (orderSellInfo != null) {
			return Mono.just(orderSellInfo);
		}
		return Mono.empty();
	}

	/**
	 * The getPortfolioUser
	 * 
	 * @param String userPortFolioId.
	 * 
	 * @param String symbol.
	 * 
	 * @return OrderInfo.
	 * 
	 * 
	 */
	public UserPortfolioResponse getPortfolioUser(String userPortFolioId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_PORTOFOLIO_USER);
		UserPortfolioResponse userPortfolioResponse = orderDetailsRespository.getUserPortfolio(userPortFolioId);
		return userPortfolioResponse;
	}

	/**
	 * The saveOrder
	 * 
	 * @param OrderInfo orderInfo
	 * 
	 * @param String    userId.
	 * 
	 * @return Mono.
	 * 
	 */
	private Mono<OrderInfo> saveOrder(OrderInfo orderInfo, User user) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.SAVE_ORDER);
		UserPortfolio userPortfolio = new UserPortfolio();
		userPortfolio.setId(CommonUtils.portfolioUserId(user));
		userPortfolio.setOrderInf(orderInfo);
		UserPortfolioResponse userPortfolioResponse = getPortfolioUser(CommonUtils.portfolioUserId(user));
		Double invesment = Double.parseDouble(orderInfo.getOrderParameters().getQuantity())
				* Double.parseDouble(orderInfo.getBuyPrice())
				+ Double.parseDouble(userPortfolioResponse.getInvestment());
		Double earning = -(Double.parseDouble(orderInfo.getOrderParameters().getQuantity()))
				* Double.parseDouble(orderInfo.getBroker().getBrokerCharges());
		Double totalEarning = userPortfolioResponse.getOrderInfo().parallelStream().reduce(0.0,
				(portfolioEarning, portfolio) -> portfolioEarning + Double.parseDouble(portfolio.getEarning()),
				Double::sum);
		Double totEarning = 0.0;
		if (userPortfolioResponse.getOrderInfo().size() != 0) {
			totEarning = earning + totalEarning;
		}
		if (earning <= 0.0 && totEarning == 0.0) {
			orderInfo.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
			orderInfo.setTotalLoss(earning.toString());
			userPortfolio.setTotalLoss(earning.toString());
			userPortfolio.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
		} else if (earning <= 0.0 && totEarning <= 0.0) {
			orderInfo.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
			orderInfo.setTotalLoss(earning.toString());
			userPortfolio.setTotalLoss(totEarning.toString());
			userPortfolio.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
		} else {
			orderInfo.setTotalProfit(earning.toString());
			orderInfo.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
			userPortfolio.setTotalProfit(totEarning.toString());
			userPortfolio.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
		}
		if (totEarning == 0.0) {
			userPortfolio.setEarning(earning.toString());
		} else {
			userPortfolio.setEarning(totEarning.toString());
		}
		userPortfolio.setInvestment(invesment.toString());
		Double overAllProfit = Double.parseDouble(userPortfolioResponse.getOverAllProfit()) + earning;
		userPortfolio.setOverAllProfit(overAllProfit.toString());
		Double income = earning + invesment;
		userPortfolio.setIncome(income.toString());
		if (invesment > Double.parseDouble(userPortfolioResponse.getAccountBalance())) {
			throw new AccountBalanceException(Constants.YOUR_CURRENT_BALANCE_IS_LOW);
		}
		Double updatedAmount = 0.0;
		updatedAmount = Double.parseDouble(userPortfolioResponse.getAccountBalance())
				- ((Double.parseDouble(orderInfo.getBuyPrice())
						+ Double.parseDouble(orderInfo.getBroker().getBrokerCharges()))
						* (Double.parseDouble(orderInfo.getOrderParameters().getQuantity())));
		user.setAccountBalance(updatedAmount.toString());
		User updatedUser = getUpdatedUser(user);
		Mono<User> u = userRepository.save(updatedUser);
		return u.flatMap(us -> {
			userPortfolio.setAccountBalance(updatedUser.getAccountBalance());
			DocumentFragment<Mutation> saveOrderSuccess = orderDetailsRespository.saveOrder(userPortfolio,
					user.getUsername());
			if (!saveOrderSuccess.id().isEmpty()) {
				try {
					updateStockQuantity(orderInfo.getOrderParameters().getSymbolId(),
							orderInfo.getOrderParameters().getSymbol(), orderInfo.getOrderParameters().getQuantity(),
							orderInfo.getOrderState().getStatus());
					Thread.sleep(10);
					saveHistoryUser(orderInfo, orderInfo.getOrderParameters().getQuantity(), us, OrderStatus.BUYING);
				} catch (Exception e) {
					LOGGER.error(LoggerConstants.ERROR_OCCURED_WHILE_SAVING_ORDER_HISTORY + e);
				}
			}
			List<OrderInfo> userPortfolioList = new ArrayList<OrderInfo>();
			userPortfolioList.add(userPortfolio.getOrderInf());
			return Mono.just(orderInfo);
		}).doOnError(error -> LOGGER.error(LoggerConstants.ERROR_WHILE_FETCHING_USER_IN_SAVE_ORDER));
	}

	/**
	 * The getUpdatedUser
	 * 
	 * @param User getUser
	 * 
	 * @return User.
	 * 
	 */
	private User getUpdatedUser(User getUser) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_UPDATE_USER);
		User updateUser = new User();
		updateUser.setAccountBalance(getUser.getAccountBalance());
		updateUser.setLastName(getUser.getLastName());
		updateUser.setId(getUser.getId());
		updateUser.setFirstName(getUser.getFirstName());
		updateUser.setEmail(getUser.getEmail());
		updateUser.setUsername(getUser.getUsername());
		updateUser.setUserId(getUser.getUserId());
		updateUser.setRoles(getUser.getRoles());
		updateUser.setPassword(getUser.getPassword());
		return updateUser;
	}

	/**
	 * The updateStockQuantity
	 * 
	 * @param String symbolId
	 * 
	 * @param String symbolName
	 *
	 * @param String userQuantity.
	 * 
	 * @param String side.
	 */
	private void updateStockQuantity(String symbolId, String symbolName, String userQuantity, String side) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.UPDATE_STOCK_QUANTITY);
		GetCurrentMarketData(symbolName, symbolId).flatMap(updateStock -> {
			Integer quantity = 0;
			if (side.equalsIgnoreCase(Constants.BUY)) {
				quantity = Integer.parseInt(updateStock.getLatestFeed().getQuantity()) - Integer.parseInt(userQuantity);
			} else {
				quantity = Integer.parseInt(updateStock.getLatestFeed().getQuantity()) + Integer.parseInt(userQuantity);
			}
			updateStock.getLatestFeed().setQuantity(quantity.toString());
			orderDetailsRespository.getCouchbaseOperations().getCouchbaseBucket()
					.mutateIn(CommonUtils.getStockId(updateStock.getSymbol(), updateStock.getSymbolId()))
					.upsert(Constants.SYMBOL, updateStock.getSymbol())
					.upsert(Constants.SYMBOL_ID, updateStock.getSymbolId())
					.upsert(Constants.LATEST_FEED, updateStock.getLatestFeed()).execute();
			return Mono.just(updateStock);
		}).subscribe();
	}

	/**
	 * The saveHistoryUser
	 * 
	 * @param OrderInfo   orderInfo
	 * 
	 * @param String      quantity.
	 * 
	 * @param User        user.
	 * 
	 * @param OrderStatus orderStatus.
	 * 
	 * 
	 */
	private void saveHistoryUser(OrderInfo orderInfo, String quantity, User user, OrderStatus orderStatus) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.SAVE_HISTORY_USER);
		LOGGER.debug(LoggerConstants.GET_ORDER_STATUS + orderStatus.getVal() + LoggerConstants.GET_ORDERINFO
				+ orderInfo.getCurrentPrice());
		OrderHistory orderHistory = new OrderHistory();
		if (orderStatus.getVal().equalsIgnoreCase(Constants.BUY)) {
			orderHistory.setBuyPrice(orderInfo.getBuyPrice());
			orderHistory.setSide(orderStatus.getVal());
			orderHistory.setSellPrice(Constants.EMPTY);
			orderHistory.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
			orderHistory.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
		}
		if (orderStatus.getVal().equalsIgnoreCase(Constants.SELL)) {
			orderHistory.setSellPrice(orderInfo.getCurrentPrice());
			orderHistory.setSide(orderStatus.getVal());
			orderHistory.setBuyPrice(Constants.ZERO_DOUBLE_VALUE);
			orderHistory.setTotalProfit(orderInfo.getTotalProfit());
			orderHistory.setTotalLoss(orderInfo.getTotalLoss());
		}
		orderHistory.setOrderId(orderInfo.getOrderId());
		orderHistory.setDuration(orderInfo.getOrderParameters().getDuration());
		orderHistory.setSymbolId(orderInfo.getOrderParameters().getSymbolId());
		orderHistory.setSymbolName(orderInfo.getOrderParameters().getSymbol());
		orderHistory.setQuantity(quantity);
		orderHistory.setUserId(user.getUserId());
		orderHistory.setUserName(user.getUsername());
		orderHistory.setTimeStamp(DateUtils.currentTimeStamp());
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Constants.ORDERHISTORY_PREFIX);
		stringBuilder.append(user.getUserId());
		orderHistory.setId(stringBuilder.toString());
		try {
			orderHistoryService.saveUserOrderHistory(orderHistory);
		} catch (Exception e) {
			LOGGER.error(LoggerConstants.ERROR_WHILE_SAVE_USER_ORDER_HISTORY);
		}
	}

	/**
	 * The prepareingSell
	 * 
	 * @param String symbolName
	 * 
	 * @param String symbol.
	 * 
	 * @param String userId.
	 * 
	 * @return Mono.
	 * 
	 */
	private Mono<SellResponse> sellOrder(String symbolName, String symbolId, String userId, String quantityField) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.PREPARING_SELL);
		Mono<User> user = getUser(userId);
		return user.flatMap(userDetails -> {
			LOGGER.debug(LoggerConstants.USER_EXIST_WITH_ID + userDetails.getUserId());
			Mono<LiveFeeds> liveFeeds = GetCurrentMarketData(symbolName, symbolId);
			return liveFeeds.flatMap(liveStocks -> {
				LOGGER.debug(LoggerConstants.LATEST_STOCK_EXIST_WITH + liveStocks.getMetaId());
				return deleteOrderInfoObject(userDetails, liveStocks.getSymbol(), symbolId, quantityField);
			}).onErrorResume(feedError -> {
				LOGGER.error(Constants.FEED_ERROR_SELL_ORDER);
				return Mono.error(feedError);
			});
		}).switchIfEmpty(Mono.error(new UserNotFound(Constants.USER_NOT_FOUND)));
	}

	/**
	 * The GetCurrentMarketData
	 * 
	 * @param String symbol
	 * 
	 * @param Sting  symbolId
	 * 
	 * @return Mono.
	 * 
	 */
	private Mono<LiveFeeds> GetCurrentMarketData(String symbol, String symbolId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_CURRENT_MARKET_DATA);
		return stockService.getlatestStockPrice(symbolId, symbol)
				.switchIfEmpty(Mono.error(new SymbolNotFound(Constants.STOCK_NOT_FOUND)));
	}

	/**
	 * The updatePortfolio.
	 * 
	 * @param LiveFeeds liveFeeds.
	 * 
	 */
	@Override
	public void updatePortfolio(LiveFeeds liveFeeds) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.UPDATE_PORTFOLIO_METHOD);
		StringBuilder portfolioId = new StringBuilder();
		portfolioId.append(Constants.PERCENTAGE);
		portfolioId.append(liveFeeds.getSymbol());
		portfolioId.append(Constants.PERCENTAGE);
		Mono<List<UserPortfolio>> findUserStockInPortofolio = orderDetailsRespository
				.findUserStockInPortofolio(portfolioId.toString()).collectList();
		findUserStockInPortofolio.flatMap(order -> {
			order.forEach(userPortfolio -> {
				LOGGER.debug(LoggerConstants.USER_PORTFOLIO_EXIST_WITH + userPortfolio.getId());
				List<OrderInfo> orderInfoDetails = userPortfolio.getListOrderInfo().stream().map(orderList -> {
					LOGGER.debug(LoggerConstants.USER_PORTFOLIO_ORDER_LIST + orderList.getOrderId());
					if (orderList.getOrderParameters().getSymbol().equals(liveFeeds.getSymbol())) {
						orderList.setCurrentPrice(liveFeeds.getLatestFeed().getCurrentPrice());
						Double earning = (Double.parseDouble(liveFeeds.getLatestFeed().getCurrentPrice())
								- Double.parseDouble(orderList.getBuyPrice()))
								* (Double.parseDouble(orderList.getOrderParameters().getQuantity()))
								- Double.parseDouble(orderList.getOrderParameters().getQuantity())
										* (Double.parseDouble(orderList.getBroker().getBrokerCharges()));
						if (earning <= 0.0) {
							orderList.setTotalProfit(Constants.ZERO_DOUBLE_VALUE);
							orderList.setTotalLoss(earning.toString());
						} else {
							orderList.setTotalProfit(earning.toString());
							orderList.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
						}
						orderList.setEarning(earning.toString());
						Double income = earning + Double.parseDouble(orderList.getInvestment());
						orderList.setIncome(income.toString());
					}
					return orderList;
				}).collect(Collectors.toList());
				Double totalEarning = userPortfolio.getListOrderInfo().parallelStream().reduce(0.0,
						(earning, orderPortfolio) -> earning + Double.parseDouble(orderPortfolio.getEarning()),
						Double::sum);
				Double income = userPortfolio.getListOrderInfo().parallelStream().reduce(0.0,
						(TotalIncome, orderPortfolio) -> TotalIncome + Double.parseDouble(orderPortfolio.getIncome()),
						Double::sum);
				Double totalProfit = userPortfolio.getListOrderInfo().parallelStream().reduce(0.0,
						(profit, orderPortfolio) -> profit + Double.parseDouble(orderPortfolio.getTotalProfit()),
						Double::sum);
				Double totalLoss = userPortfolio.getListOrderInfo().parallelStream().reduce(0.0,
						(loss, orderPortfolio) -> loss + Double.parseDouble(orderPortfolio.getTotalLoss()),
						Double::sum);
				if (totalProfit > 0.0) {
					totalProfit = totalProfit + totalLoss;
					totalLoss = 0.0;
				} else {
					totalProfit = 0.0;
					totalLoss = totalProfit + totalLoss;
				}
				orderDetailsRespository.updatePortfolioLiveFeeds(userPortfolio, orderInfoDetails, totalEarning, income,
						totalProfit, totalLoss);
			});
			return Mono.just(order);
		}).subscribe();
	}

	/**
	 * The getUserPreSellOrder.
	 * 
	 * @param String symbolId.
	 * 
	 * @param String symbol.
	 * 
	 * @param String userId.
	 * 
	 * @param String quantity.
	 * 
	 * @return Mono.
	 **/
	@Override
	public Mono<SellResponse> getUserPreSellOrder(String symbolId, String symbol, String userId, String quantity) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER_PRE_ORDER_METHOD);
		return getUser(userId).flatMap(user -> {
			Mono<LiveFeeds> liveFeeds = GetCurrentMarketData(symbol, symbolId);
			return liveFeeds.flatMap(stocks -> {
				UserPortfolioResponse userPortfolioResponse = getPortfolioUser(CommonUtils.portfolioUserId(user));
				return checkSellOrderExist(user, stocks, userPortfolioResponse).flatMap(orderSellInfo -> {
					SellResponse sellResponse = new SellResponse();
					if (Integer.parseInt(orderSellInfo.getOrderParameters().getQuantity()) >= Integer
							.parseInt(quantity)) {
						sellResponse.setQuantity(orderSellInfo.getOrderParameters().getQuantity());
						sellResponse.setSymbolName(symbol);
						sellResponse.setOrderId(orderSellInfo.getOrderId());
						sellResponse.setSellPrice(orderSellInfo.getCurrentPrice());
						sellResponse.setBuyPrice(orderSellInfo.getBuyPrice());
						Double investment = Double.parseDouble(orderSellInfo.getBuyPrice())
								* Double.parseDouble(quantity);
						sellResponse.setInvestment(investment.toString());
						sellResponse.setStatus(Constants.PRE_SELL);
						Double totalProfit = (Double.parseDouble(orderSellInfo.getCurrentPrice())
								- Double.parseDouble(orderSellInfo.getBuyPrice())) * Double.parseDouble(quantity);
						Double totalValue = investment + totalProfit;
						sellResponse.setTotalValue(totalValue.toString());
						if (totalProfit <= 0.0) {
							sellResponse.setTotalprofit(Constants.ZERO_DOUBLE_VALUE);
							sellResponse.setTotalLoss(totalProfit.toString());
						} else {
							sellResponse.setTotalprofit(totalProfit.toString());
							sellResponse.setTotalLoss(Constants.ZERO_DOUBLE_VALUE);
						}
						return Mono.just(sellResponse);
					}
					return Mono
							.error(new NotValidQuantity(Constants.DONOT_HAVE_QUANITY + quantity + Constants.QUANTITY));
				}).switchIfEmpty(Mono.error(new SellOrderNotFound(Constants.SELL_ORDER_NOT_FOUND)));
			}).doOnError(error -> LOGGER.error(LoggerConstants.get_ALL_LATEST_STOCK_ERROR_PRESELL));
		}).switchIfEmpty(Mono.error(new UserNotFound(Constants.USER_NOT_FOUND)));
	}
}
