package com.usermanagementservice.service;

import com.usermanagementservice.model.OrderHistory;
import com.usermanagementservice.model.OrderHistoryResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface OrderHistoryService
 * 
 * @author harsh.jain
 *
 */
public interface OrderHistoryService {

	/**
	 * The getUserOrderHistory.
	 * 
	 * @param String     userOrderId.
	 * 
	 * @param startDate.
	 *
	 * @param endDate.
	 * 
	 * @param String     side.
	 * 
	 * @return Mono.
	 *
	 */
	Mono<OrderHistoryResponse> getUserOrderHistory(String userOrderId, String startDate, String endDate, String side,String symbol);

	/**
	 * The saveUserOrderHistory.
	 * 
	 * @param OrderHistory orderHistory.
	 *
	 */
	void saveUserOrderHistory(OrderHistory orderHistory);

	/**
	 * The getAllUserOrderHistory.
	 * 
	 * @param String startDate.
	 * 
	 * @param String endDate.
	 * 
	 * @return Mono.
	 *
	 */
	Flux<OrderHistoryResponse> getAllUserOrderHistory(String startDate, String endDate);

}
