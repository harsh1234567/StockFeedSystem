package com.filestockstorageservice.repository;

import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.filestockstorageservice.constants.Constant;
import com.filestockstorageservice.model.StockRecords;

/**
 * The Interface StockDetailRepository.
 * 
 * @author harsh.jain
 *
 */
@Repository
public interface StockDetailRepository extends ReactiveCouchbaseRepository<StockRecords, String> {

	/**
	 * save stock records
	 * 
	 * @param StockRecords stockRecords.
	 * 
	 * @return
	 */
	default void saveStockRecords(StockRecords stockRecords) {
		getCouchbaseOperations().getCouchbaseBucket().mutateIn(stockRecords.getMetaId()).upsertDocument(true)
				.upsert(Constant.SYMBOL_NAME, stockRecords.getSymbolName())
				.upsert(Constant.SYMBOL_ID, stockRecords.getSymbolId())
				.upsert(Constant.LATEST_FEED, stockRecords.getListOfSymbols().get(0))
				.arrayAppend(Constant.LIST_OF_SYMBOL, stockRecords.getListOfSymbols().get(0)).execute();
	}

}
