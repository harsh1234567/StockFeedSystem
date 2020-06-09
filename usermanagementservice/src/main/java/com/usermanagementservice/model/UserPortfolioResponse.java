package com.usermanagementservice.model;

import java.util.List;

import com.couchbase.client.java.repository.annotation.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class UserPortfolioResponse.
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPortfolioResponse {
	/** The id */
	private String id;

	/** The userName */
	@Field("userName")
	private String userName;

	/** The orderInfo */
	@Field("orderInfo")
	private List<OrderInfo> orderInfo;

	/** The totalProfit */
	@Field("totalProfit")
	private String totalProfit;

	/** The totalLoss */
	@Field("totalLoss")
	private String totalLoss;

	/** The investment. */
	@Field("investment")
	private String investment;

	/** The earning. */
	@Field("earning")
	private String earning;

	/** The income. */
	@Field("income")
	private String income;

	/** The overAllProfit. */
	@Field("overAllProfit")
	private String overAllProfit;

	/** The accountBalance. */
	@Field("accountBalance")
	private String accountBalance;

	/** The count. */
	private String count;

}
