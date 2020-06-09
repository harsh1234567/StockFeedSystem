package com.usermanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class LatestFeed.
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatestFeed {

	/** The openPrice */
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
