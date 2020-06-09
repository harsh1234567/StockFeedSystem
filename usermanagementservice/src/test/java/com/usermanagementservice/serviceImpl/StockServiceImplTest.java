package com.usermanagementservice.serviceImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.usermanagementservice.constants.ConstantsTest;
import com.usermanagementservice.model.LiveFeeds;
import com.usermanagementservice.respository.StockLatestFeedDetailRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The class StockServiceImplTest.
 * 
 * @author harsh.jain
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {
	/**
	 * The stockServiceImpl
	 */
	@InjectMocks
	private StockServiceImpl stockServiceImpl;

	/** The stockServiceImpl **/
	@Mock
	private StockLatestFeedDetailRepository stockLatestFeedDetailRepository;

	/**
	 * the getAllLatestStock
	 * 
	 */
	@Test
	public void getAllLatestStockTest() {
		Mockito.when(stockLatestFeedDetailRepository.findAllById(Mockito.anyString()))
				.thenReturn(Flux.just((porfolioList())));
		Flux<LiveFeeds> newStockMono = stockServiceImpl.getAlllatestStockPrice();
		StepVerifier.create(newStockMono).assertNext(response -> {
			Assert.assertNotNull(response);
		}).verifyComplete();
	}

	/**
	 * the getLatestStock
	 * 
	 */
	@Test
	public void getLatestStockTest() {
		Mockito.when(stockLatestFeedDetailRepository.findAllBySymbolId(Mockito.anyString()))
				.thenReturn(Mono.just((porfolioList())));
		Mono<LiveFeeds> newStockMono = stockServiceImpl.getlatestStockPrice(ConstantsTest.TEN, ConstantsTest.SYMBOL);
		StepVerifier.create(newStockMono).assertNext(response -> {
			Assert.assertNotNull(response);
		}).verifyComplete();
	}

	/**
	 * The porfolioList
	 * 
	 * @return LiveFeeds
	 **/
	private LiveFeeds porfolioList() {
		LiveFeeds livefeeds = new LiveFeeds();
		livefeeds.setSymbol(ConstantsTest.SYMBOL);
		livefeeds.setSymbolId(ConstantsTest.TEN);
		return livefeeds;
	}

}