package com.usermanagementservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagementservice.constants.ApiEndpointsConstant;
import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.model.LiveFeeds;
import com.usermanagementservice.service.StockService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The stock controller.
 *
 * @author harsh.jain
 *
 */
@RestController
@RequestMapping(ApiEndpointsConstant.STOCK_API)
public class StockController {

	/** The stockService */
	@Autowired
	private StockService stockService;

	/** The LOGGER. */
	private Logger LOGGER = LoggerFactory.getLogger(StockController.class);

	/**
	 * The getAlllatestStockPrice
	 * 
	 * @return Mono
	 */
	@GetMapping(ApiEndpointsConstant.GET_ALL_LATEST_STOCK_API)
	@PreAuthorize(Constants.ROLE_ADMIN_USER)
	public Flux<LiveFeeds> getAlllatestStockPrice() {
		LOGGER.info(LoggerConstants.RECEIVED_ALL_LATEST_STOCK);
		return stockService.getAlllatestStockPrice().onErrorResume(stockError -> {
			LOGGER.error(LoggerConstants.GET_ALL_LATEST_STOCK_ERROR);
			return Mono.error(stockError);
		});
	}

	/**
	 * The getlatestStockPrice
	 * 
	 * @param String symbolName
	 * 
	 * @return Mono
	 * 
	 */
	@GetMapping(ApiEndpointsConstant.GET_LATEST_STOCK_API)
	@PreAuthorize(Constants.ROLE_ADMIN_USER)
	public Mono<LiveFeeds> getlatestStockPrice(@RequestParam(Constants.SYMBOL_NAME) String symbolName,
			@RequestParam(Constants.SYMBOL_ID) String symbolId) {
		LOGGER.info(LoggerConstants.RECEIVED_LATEST_STOCK);
		return stockService.getlatestStockPrice(symbolId, symbolName).onErrorResume(stockError -> {
			LOGGER.error(LoggerConstants.GET_LATEST_STOCK_API + symbolName);
			return Mono.error(stockError);
		});
	}
}
