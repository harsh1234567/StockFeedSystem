package com.usermanagementservice.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagementservice.constants.ApiEndpointsConstant;
import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.exception.GlobalException;
import com.usermanagementservice.model.OrderDetails;
import com.usermanagementservice.model.OrderHistoryResponse;
import com.usermanagementservice.model.OrderInfo;
import com.usermanagementservice.model.sell.SellResponse;
import com.usermanagementservice.service.OrderHistoryService;
import com.usermanagementservice.service.UserPortfolioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The class PortfolioController
 * 
 * @author harsh.jain
 *
 */
@RestController
@RequestMapping(ApiEndpointsConstant.PORTFOLIO_API)
public class PortfolioController {

	/** The logger */
	private final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

	/** The UserPortfolioService */
	@Autowired
	private UserPortfolioService userPortfolioService;

	/** The OrderHistoryService */
	@Autowired
	private OrderHistoryService orderHistoryService;

	/**
	 * method buy
	 * 
	 * @param orderDetails.
	 * 
	 * @return Mono.
	 */
	@PostMapping(ApiEndpointsConstant.BUY_API)
	@PreAuthorize(Constants.ROLE_USER)
	public Mono<OrderInfo> buy(@Valid @RequestBody OrderDetails orderDetails) {
		Optional<OrderDetails> optionalorderDetails = Optional.ofNullable(orderDetails);
		if (optionalorderDetails.isPresent()) {
			return userPortfolioService.buyStock(optionalorderDetails.get());
		} else {
			logger.error(LoggerConstants.EMPTY_PAYLOAD);
			throw new GlobalException(HttpStatus.BAD_REQUEST, Constants.EMPTY_REQUEST_PAYLOAD);
		}

	}

	/**
	 * method sell
	 * 
	 * @param String symbolName.
	 * 
	 * @param String symbolId.
	 * 
	 * @param String userId.
	 * 
	 * @return Mono.
	 */
	@DeleteMapping(ApiEndpointsConstant.SELL_API)
	@PreAuthorize(Constants.ROLE_USER)
	public Mono<SellResponse> sell(@RequestParam(Constants.SYMBOL) String symbolName,
			@RequestParam(Constants.SYMBOL_ID) String symbolId, @RequestParam(Constants.USER_ID) String userId,
			@RequestParam(Constants.QUANTITY) String quantity) {
		logger.info(LoggerConstants.SELL_INFO);
		return userPortfolioService.sellStock(symbolName, symbolId, userId, quantity);
	}

	/**
	 * method get user Order History
	 * 
	 * @param String     userId.
	 * 
	 * @param startDate.
	 * 
	 * @param endDate.
	 * 
	 * @param side.
	 * 
	 * @return Mono.
	 */
	@GetMapping(ApiEndpointsConstant.GET_USER_ORDER_HISTORY)
	@PreAuthorize(Constants.ROLE_USER)
	public Mono<OrderHistoryResponse> getUserOrderHistory(@RequestParam(Constants.USER_ID) String userId,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) String side, @RequestParam(required = false) String symbol) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_ORDER_HSITROY_CONTROLLER);
		return orderHistoryService.getUserOrderHistory(userId, startDate, endDate, side, symbol);
	}

	/**
	 * method get user All Order History
	 * 
	 * @param startDate.
	 * 
	 * @param endDate.
	 * 
	 * @return Mono.
	 */
	@GetMapping(ApiEndpointsConstant.GET_ALL_USER_ORDER_HISTORY)
	@PreAuthorize(Constants.ROLE_ADMIN)
	public Flux<OrderHistoryResponse> getAllUserOrderHistory(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_ALL_USER_PORTFOLIO_METHOD);
		return orderHistoryService.getAllUserOrderHistory(startDate, endDate);
	}

	/**
	 * method get user All Order History
	 * 
	 * @param startDate.
	 * 
	 * @param endDate.
	 * 
	 * @return Mono.
	 */
	@GetMapping(ApiEndpointsConstant.GET_USER_PRE_SELL_ORDER_API)
	@PreAuthorize(Constants.ROLE_ADMIN_USER)
	public Mono<SellResponse> getUserPreSellOrder(@RequestParam(Constants.USER_ID) String userId,
			@RequestParam(Constants.SYMBOL) String symbol, @RequestParam(Constants.SYMBOL_ID) String symbolId,
			@RequestParam(Constants.QUANTITY) String quantity) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_USER_PRE_SELL_ORDER_METHOD);
		return userPortfolioService.getUserPreSellOrder(symbolId, symbol, userId, quantity)
				.doOnError(error -> logger.error(LoggerConstants.USER_PORTFOLIO_EXCEPTION));
	}
}
