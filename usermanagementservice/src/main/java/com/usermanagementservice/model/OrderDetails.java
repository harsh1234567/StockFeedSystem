package com.usermanagementservice.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Order Details
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

	/** The status */
	@NotNull
	@NotEmpty
	private String status;

	/** The userId */
	@NotNull
	@NotEmpty
	private String userId;

	/** The symbolId */
	@NotNull
	@NotEmpty
	private String symbolId;

	/** The symbol */
	@NotNull
	@NotEmpty
	private String symbol;

	/** The quantity */
	private String quantity;

	/** The price */
	private String price;

	/** The bidPriceFlag */
	@NotNull
	private Boolean bidPriceFlag;

	/** The brokerId */
	private String brokerId;

	/** The curretnAmount */
	private String curretnAmount;

	/** The bidPrice */
	private String bidPrice;

	/** The duration */
	@NotNull
	@NotEmpty
	private String duration;

}
