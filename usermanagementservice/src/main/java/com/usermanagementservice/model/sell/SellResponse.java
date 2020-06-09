package com.usermanagementservice.model.sell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class SellResponse.
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellResponse {

	/** The orderId */
	private String orderId;

	/** The symbolName */
	private String symbolName;

	/** The quantity */
	private String quantity;

	/** The status */
	private String status;

	/** The sellPrice */
	private String sellPrice;

	/** The buyPrice */
	private String buyPrice;

	/** The investment */
	private String investment;

	/** The totalprofit */
	private String totalprofit;

	/** The totalValue */
	private String totalValue;

	/** The totalLoss */
	private String totalLoss;
}
