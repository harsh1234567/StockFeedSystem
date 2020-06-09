package com.filestockstorageservice.model;

import org.springframework.data.couchbase.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class StockDetails.
 * 
 * @author harsh.jain
 *
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetails {

	/** The openPrice. */
	private String openPrice;

	/** The highestPrice. */
	private String highestPrice;

	/** The low. */
	private String low;

	/** The lastPrice. */
	private String lastPrice;

	/** The quantity */
	private String quantity;

	/** The currentPrice. */
	private String currentPrice;

	/** The timeStamp */
	private String timeStamp;

}
