package com.filestockstorageservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class Stock Response
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {

	/** The symbol */
	private String symbol;
	
	/** The symbolId */
	private String symbolId;
	
	/** The latestFeed */
	private Object latestFeed;

}
