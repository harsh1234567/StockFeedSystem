package com.filestockstorageservice.model;

import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The StockRecords.
 * 
 * @author harsh.jain
 *
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRecords {

	@Id
	private String metaId;

	private List<StockDetails> listOfSymbols;

	private String symbolName;

	private String symbolId;

	private StockDetails latestFeed;

}
