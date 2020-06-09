package com.usermanagementservice.model;

import com.usermanagementservice.model.broker.Broker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class OrderInfo
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
	
	/** The orderId */
	private String orderId;

	/** The placeTime */
	private String placeTime;

	/** The orderState */
	private OrderState orderState;

	/** The broker */
	private Broker broker;

	/** The userId */
	private String userId;

	/** The buyPrice */
	private String buyPrice;

	/** The earning */
	private String earning;

	/** The totalProfit */
	private String totalProfit;

	/** The totalLoss */
	private String totalLoss;

	/** The income */
	private String income;

	/** The investment */
	private String investment;

	/** The bid Price */
	private String bidPrice;

	/** The current price */
	private String currentPrice;

	/** The orderParameters. */
	private OrderParameters orderParameters;


}
