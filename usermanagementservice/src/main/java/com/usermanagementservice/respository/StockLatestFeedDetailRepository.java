package com.usermanagementservice.respository;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.model.LiveFeeds;
import com.usermanagementservice.utils.CommonUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface StockLatestFeedDetailRepository
 * 
 * @author harsh.jain
 *
 */
@Repository
public interface StockLatestFeedDetailRepository extends ReactiveCouchbaseRepository<LiveFeeds, String> {

	/**
	 * the findBysymbol details
	 * 
	 * @Param symbol.
	 * 
	 * @return Mono.
	 */
	Mono<LiveFeeds> findBySymbol(String symbol);

	/**
	 * the findAllById details
	 * 
	 * @Param metaId.
	 * 
	 * @return Flux.
	 */
	@Query("select symbolId,symbol,latestFeed ,META().id AS _ID, META().cas AS _CAS from ums where META().id LIKE $1")
	Flux<LiveFeeds> findAllById(String metaId);

	/**
	 * the saveLatestStocks details
	 * 
	 * @Param liveStockDetails.
	 * 
	 * 
	 */
	default void saveLatestStocks(LiveFeeds liveFeeds) {
		
		getCouchbaseOperations().getCouchbaseBucket()
				.mutateIn(CommonUtils.getStockId(liveFeeds.getSymbol(), liveFeeds.getSymbolId())).upsertDocument(true)
				.upsert(Constants.SYMBOL, liveFeeds.getSymbol()).upsert(Constants.SYMBOL_ID, liveFeeds.getSymbolId())
				.upsert(Constants.LATEST_FEED, liveFeeds.getLatestFeed()).execute();
	}

	/**
	 * the findAllBySymbolId
	 * 
	 * @Param String stockId.
	 * 
	 * @return Mono.
	 * 
	 */
	@Query("select symbolId,symbol,latestFeed ,META().id AS _ID, META().cas AS _CAS from ums where META().id LIKE $1")
	Mono<LiveFeeds> findAllBySymbolId(String stockId);

}
