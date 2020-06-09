package com.usermanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Stock Response
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveStockDetails   {
	
	/**The symbol*/
	private String symbol;
	/**The symbolId*/
	private String symbolId;
	
	/**The latestFeed*/
	private LatestFeed latestFeed;

}
