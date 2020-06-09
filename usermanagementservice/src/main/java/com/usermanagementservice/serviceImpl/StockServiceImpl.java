package com.usermanagementservice.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.exception.NoSymbolListFound;
import com.usermanagementservice.exception.SymbolNotFound;
import com.usermanagementservice.model.LiveFeeds;
import com.usermanagementservice.respository.StockLatestFeedDetailRepository;
import com.usermanagementservice.service.StockService;
import com.usermanagementservice.utils.CommonUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The Class StockServiceImpl.
 * 
 * @author harsh.jain
 *
 */
@Service
public class StockServiceImpl implements StockService {

	/** The stockLatestFeedDetailRepository */
	@Autowired
	private StockLatestFeedDetailRepository stockLatestFeedDetailRepository;

	@Autowired
	private StockService stockService;

	/** The LOGGER. */
	private Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

	/**
	 * The getAlllatestStockPrice
	 * 
	 * @return Mono.
	 */
	@Override
	public Flux<LiveFeeds> getAlllatestStockPrice() {
		LOGGER.info(Constants.METHOD_INVOKE, LoggerConstants.GET_ALL_STOCK);
		StringBuilder getMetakey = new StringBuilder(Constants.STOCK_PREFIX);
		getMetakey.append(Constants.PERCENTAGE);
		Flux<LiveFeeds> findAllById = stockLatestFeedDetailRepository.findAllById(getMetakey.toString());
		return findAllById.flatMap(stock -> {
			LOGGER.debug(LoggerConstants.GET_LATEST_SAVE + stock);
			return Flux.just(stock);
		}).switchIfEmpty(sym -> Mono.error(new NoSymbolListFound(Constants.SYMBOL_LIST_NOT_FOUND)));
	}

	/**
	 * The getlatestStockPrice.
	 * 
	 * @param symbolId
	 * 
	 * @param symbol.
	 * 
	 * @return Mono.
	 */
	@Override
	public Mono<LiveFeeds> getlatestStockPrice(String symbolId, String symbol) {
		LOGGER.info(Constants.METHOD_INVOKE, LoggerConstants.GET_STOCK);
		Mono<LiveFeeds> findAllById = stockLatestFeedDetailRepository
				.findAllBySymbolId(CommonUtils.getStockId(symbol, symbolId));
		return findAllById.flatMap(stock -> {
			LOGGER.debug(LoggerConstants.GET_LATEST_STOCK_SAVE + stock);
			return Mono.just(stock);
		}).switchIfEmpty(Mono.error(new SymbolNotFound(Constants.SYMBOL_NOT_FOUND)));
	}

	/**
	 * The saveLatestFeed.
	 * 
	 * @param LiveFeeds liveFeeds.
	 * 
	 */
	@Override
	public void saveLatestFeed(LiveFeeds liveFeeds) {
		Mono<LiveFeeds> feeds = stockService.getlatestStockPrice(liveFeeds.getSymbolId(), liveFeeds.getSymbol())
				.flatMap((stocks) -> {
					Integer quantity = 0;
					if (liveFeeds.getSymbol() != null) {
						if (liveFeeds.getLatestFeed().getQuantity().equals(Constants.ZERO)) {
							quantity = Integer.parseInt(stocks.getLatestFeed().getQuantity());
						} else {
							quantity = Integer.parseInt(stocks.getLatestFeed().getQuantity())
									+ Integer.parseInt(liveFeeds.getLatestFeed().getQuantity());
						}
					}
					liveFeeds.getLatestFeed().setQuantity(quantity.toString());
					return Mono.just(liveFeeds);
				}).switchIfEmpty(Mono.just(liveFeeds));
		LOGGER.info(Constants.SAVE_INTO_UMS);
		stockLatestFeedDetailRepository.saveLatestStocks(feeds.block());
	}
}
