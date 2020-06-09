package com.usermanagementservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class LiveStockPrice
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveStockPrice {

	/** The symbol */
	private String symbol;

	/** The symbolId */
	private String symbolId;

	/** The latestFeed */
	@JsonProperty("latestFeed")
	private LatestFeed latestFeed;

}
