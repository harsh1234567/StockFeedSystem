package com.usermanagementservice.service;

import com.usermanagementservice.model.LiveFeeds;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The class StockService
 * 
 * @author harsh.jain
 *
 */
public interface StockService {

	/**
	 * The getAlllatestStockPrice
	 * 
	 * @return Mono.
	 *
	 */
	Flux<LiveFeeds> getAlllatestStockPrice();

	/**
	 * The getlatestStockPrice
	 * 
	 * @param symbolId
	 * 
	 * @param symbol
	 * 
	 * @return Mono.
	 *
	 */
	Mono<LiveFeeds> getlatestStockPrice(String symbolId, String symbol);

	/**
	 * The saveLatestFeed
	 * 
	 * @param liveFeeds
	 *
	 */
	void saveLatestFeed(LiveFeeds liveFeeds);

}
