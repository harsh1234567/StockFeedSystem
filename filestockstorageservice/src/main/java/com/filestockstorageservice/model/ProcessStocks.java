package com.filestockstorageservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class ProcessStocks.
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessStocks {

	/** The symbolId. */
	private String symbolId;

	/** The symbolName. */
	private String symbolName;

	/** The highestPrice. */
	private String openPrice;

	/** The highestPrice. */
	private String highestPrice;

	/** The low. */
	private String low;

	/** The lastPrice. */
	private String lastPrice;

	/** The currentPrice. */
	private String currentPrice;

	/** The quantity */
	private String quantity;

	/** The timeStamp */
	private String timeStamp;
}
