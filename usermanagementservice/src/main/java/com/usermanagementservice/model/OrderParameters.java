package com.usermanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class OrderParameters.
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderParameters {

	/** The side */
	private String side;

	/** The duration */
	private String duration;

	/** The quantity */
	private String quantity;

	/** The symbol */
	private String symbol;
	
	/** The symbolId */
	private String symbolId;
}
