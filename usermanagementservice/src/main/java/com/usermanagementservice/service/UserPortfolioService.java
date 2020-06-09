package com.usermanagementservice.service;

import com.usermanagementservice.model.LiveFeeds;
import com.usermanagementservice.model.OrderDetails;
import com.usermanagementservice.model.OrderInfo;
import com.usermanagementservice.model.broker.Broker;
import com.usermanagementservice.model.sell.SellResponse;

import reactor.core.publisher.Mono;

/**
 * The Class UserPortfolioService
 * 
 * @author harsh.jain
 *
 */
public interface UserPortfolioService {

	/**
	 * The buyStock
	 * 
	 * @param orderDetails.
	 * 
	 * @return Mono.
	 * 
	 */
	Mono<OrderInfo> buyStock(OrderDetails orderDetails);

	/**
	 * The sellStock
	 * 
	 * @param symbolId.
	 *
	 * @param userId
	 * 
	 * @return Mono.
	 * 
	 */
	Mono<SellResponse> sellStock(String symbolName, String symbolId, String userId, String quantityField);

	/**
	 * The brokerCall
	 * 
	 * @param duration.
	 *
	 * 
	 */
	Mono<Broker> brokerCall(String duration);

	/**
	 * The brokerCall
	 * 
	 * @param duration.
	 *
	 */
	void updatePortfolio(LiveFeeds liveFeeds);

	/**
	 * The getUserPreSellOrder
	 * 
	 * @param String symbolId.
	 * 
	 * @param String symbol.
	 * 
	 * @param String userId.
	 *
	 * @param String quantity.
	 *
	 */
	Mono<SellResponse> getUserPreSellOrder(String symbolId, String symbol, String userId, String quantity);

}
