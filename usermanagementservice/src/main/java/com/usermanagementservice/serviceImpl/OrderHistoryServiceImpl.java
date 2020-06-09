package com.usermanagementservice.serviceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.exception.UserNotFound;
import com.usermanagementservice.exception.UserOrderHistoryNotFound;
import com.usermanagementservice.model.OrderHistory;
import com.usermanagementservice.model.OrderHistoryResponse;
import com.usermanagementservice.model.UserOrderHistory;
import com.usermanagementservice.respository.OrderHistoryRepository;
import com.usermanagementservice.service.OrderHistoryService;
import com.usermanagementservice.service.UserService;
import com.usermanagementservice.utils.CommonUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The class OrderHistoryServiceImpl.
 * 
 * @author harsh.jain
 *
 */
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

	/** The OrderHistoryRepository */
	@Autowired
	private OrderHistoryRepository orderHistoryRepository;

	/** The userService */
	@Autowired
	private UserService userService;

	/** The LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryServiceImpl.class);

	/**
	 * The saveUserOrderHistory.
	 * 
	 * @param OrderHistory orderHistory.
	 */
	@Override
	public void saveUserOrderHistory(OrderHistory orderHistory) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.SAVE_USER_ORDER_HISTORY_METHOD);
		try {
			LOGGER.debug(LoggerConstants.SAVE_HISTORY_WITH_ID + orderHistory.getId());
			orderHistoryRepository.saveUserHistoryOrder(orderHistory);
			LOGGER.info(LoggerConstants.SAVE_ORDER_HISTORY);
		} catch (Exception ex) {
			LOGGER.error(LoggerConstants.ERROR_OCCURED_WHILE_SAVING_ORDER_HISTORY + ex.getMessage());
		}
	}

	/**
	 * The getUserOrderHistory.
	 * 
	 * @param String userOrderId.
	 * 
	 * @param String startDate.
	 * 
	 * @param String endDate.
	 * 
	 * @return Mono.
	 */
	@Override
	public Mono<OrderHistoryResponse> getUserOrderHistory(String userOrderId, String startDate, String endDate,
			String side, String symbol) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER_ORDER_HISTORY_METHOD);
		return userService.getUser(userOrderId).flatMap((user) -> {
			return orderHistoryRepository.findAllOrder(Constants.ORDERHISTORY_PREFIX + userOrderId).flatMap((order) -> {
				LOGGER.debug(LoggerConstants.GET_ORDER_ID + order.getId());
				OrderHistoryResponse orderResponse = new OrderHistoryResponse();
				StringBuilder orderHistoryKey = new StringBuilder();
				orderHistoryKey.append(Constants.ORDERHISTORY_PREFIX);
				orderHistoryKey.append(userOrderId);
				orderResponse.setId(orderHistoryKey.toString());
				List<UserOrderHistory> userOrderHistoryList = CommonUtils.collectionToStream(order.getOrderHistory())
						.map(responseOrder -> {
							UserOrderHistory userOrderHistory = userOrderHistory(responseOrder);
							return userOrderHistory;
						}).collect(Collectors.toCollection(LinkedList::new));
				List<UserOrderHistory> uorderHistory = userOrderHistoryList;
				if (!ObjectUtils.isEmpty(startDate) && !ObjectUtils.isEmpty(endDate)) {
					uorderHistory = uorderHistory.stream()
							.filter((a) -> CommonUtils.dateRangeWithIn(a.getTimeStamp(), startDate, endDate))
							.collect(Collectors.toList());
				}
				if (side != null) {
					uorderHistory = uorderHistory.stream().filter((a) -> a.getSide().equalsIgnoreCase(side))
							.collect(Collectors.toList());
				}
				if (!ObjectUtils.isEmpty(symbol)) {
					uorderHistory = uorderHistory.parallelStream().filter((sym) -> symbol.equals(sym.getSymbolName()))
							.collect(Collectors.toList());
				}
				Double totalLoss = uorderHistory.parallelStream().reduce(0.0,
						(loss, orderPortfolio) -> loss + Double.parseDouble(orderPortfolio.getTotalLoss()),
						Double::sum);
				Double totalProfit = uorderHistory.parallelStream().reduce(0.0,
						(profit, orderPortfolio) -> profit + Double.parseDouble(orderPortfolio.getTotalProfit()),
						Double::sum);
				Double profit = totalLoss + totalProfit;
				orderResponse.setOverAllProfit(profit.toString());
				orderResponse.setCount(String.valueOf(uorderHistory.size()));
				orderResponse.setUserOrderHistory(uorderHistory);
				orderResponse.setUserId(order.getUserId());
				orderResponse.setUserName(order.getUserName());
				return Mono.just(orderResponse);
			}).doOnError(e -> LOGGER.error(LoggerConstants.ERROR_FETCH_ORDER_HISTORY))
					.switchIfEmpty(Mono.error(new UserOrderHistoryNotFound(Constants.USER_ORDER_NOT_FOUND)));
		}).switchIfEmpty(Mono.error(new UserNotFound(Constants.USER_NOT_FOUND)));
	}

	/**
	 * The userOrderHistory.
	 * 
	 * @param OrderHistory orderHistory.
	 * 
	 * @return UserOrderHistory.
	 * 
	 */
	private UserOrderHistory userOrderHistory(OrderHistory responseOrder) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER_ORDER_HISTORY_METHOD);
		UserOrderHistory userOrderHistory = new UserOrderHistory();
		userOrderHistory.setUserId(responseOrder.getUserId());
		userOrderHistory.setOrderId(responseOrder.getOrderId());
		userOrderHistory.setSymbolName(responseOrder.getSymbolName());
		userOrderHistory.setSymbolId(responseOrder.getSymbolId());
		userOrderHistory.setUserName(responseOrder.getUserName());
		userOrderHistory.setTimeStamp(responseOrder.getTimeStamp());
		userOrderHistory.setQuantity(responseOrder.getQuantity());
		userOrderHistory.setBuyPrice(responseOrder.getBuyPrice());
		userOrderHistory.setSide(responseOrder.getSide());
		userOrderHistory.setTotalProfit(responseOrder.getTotalProfit());
		userOrderHistory.setTotalLoss(responseOrder.getTotalLoss());
		userOrderHistory.setDuration(responseOrder.getDuration());
		userOrderHistory.setSellPrice(responseOrder.getSellPrice());
		return userOrderHistory;
	}

	/**
	 * The getAllUserOrderHistory.
	 * 
	 * @param String startDate.
	 * 
	 * @param String endDate.
	 * 
	 * @return Mono.
	 */
	public Flux<OrderHistoryResponse> getAllUserOrderHistory(String startDate, String endDate) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_ALL_USER_ORDER_HISTORY_METHOD);
		StringBuilder orderHistoryId = new StringBuilder();
		orderHistoryId.append(Constants.PERCENTAGE);
		orderHistoryId.append(Constants.ORDERHISTORY_PREFIX);
		orderHistoryId.append(Constants.PERCENTAGE);
		return orderHistoryRepository.findAllUserOrder(orderHistoryId.toString()).flatMap((order) -> {
			LOGGER.debug(LoggerConstants.GET_ORDER_ID + order.getId());
			OrderHistoryResponse orderResponse = new OrderHistoryResponse();
			orderResponse.setId(Constants.ORDERHISTORY_PREFIX + order.getUserId());
			List<UserOrderHistory> userOrderHistoryList = CommonUtils.collectionToStream(order.getOrderHistory())
					.map(responseOrder -> {
						UserOrderHistory userOrderHistory = userOrderHistory(responseOrder);
						return userOrderHistory;
					}).collect(Collectors.toCollection(LinkedList::new));
			if (!ObjectUtils.isEmpty(startDate) && ObjectUtils.isEmpty(endDate)) {
				userOrderHistoryList = userOrderHistoryList.stream()
						.filter((a) -> CommonUtils.dateRangeWithIn(a.getTimeStamp(), startDate, endDate))
						.collect(Collectors.toList());
			}
			LOGGER.info(LoggerConstants.GET_ORDER_HISTORY_LIST_COUNT + order.getOrderHistory());
			Double totalLoss = CommonUtils.collectionToStream(userOrderHistoryList).reduce(0.0,
					(loss, orderPortfolio) -> loss + Double.parseDouble(orderPortfolio.getTotalLoss()), Double::sum);
			Double totalProfit = CommonUtils.collectionToStream(userOrderHistoryList).reduce(0.0,
					(profit, orderPortfolio) -> profit + Double.parseDouble(orderPortfolio.getTotalProfit()),
					Double::sum);
			Double profit = totalLoss + totalProfit;
			orderResponse.setOverAllProfit(profit.toString());
			orderResponse.setCount(String.valueOf(userOrderHistoryList.size()));
			orderResponse.setUserOrderHistory(userOrderHistoryList);
			orderResponse.setUserId(order.getUserId());
			orderResponse.setUserName(order.getUserName());
			return Flux.just(orderResponse);
		}).onErrorResume(error -> Mono.empty());
	}
}
