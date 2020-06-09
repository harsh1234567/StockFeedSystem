package com.usermanagementservice.model;

import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class UserPortfolio.
 * 
 * @author harsh.jain
 *
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPortfolio {

	/** The id */
	@Id
	private String id;

	/** The userName */
	@Field("userName")
	private String userName;

	/** The orderInfo */
	private OrderInfo orderInf;

	@Field("orderInfo")
	/** The List orderInfo */
	private List<OrderInfo> listOrderInfo;

	/** The totalProfit */
	@Field("totalProfit")
	private String totalProfit;

	/** The totalLoss */
	@Field("totalLoss")
	private String totalLoss;

	/** The investment */
	@Field("investment")
	private String investment;

	/** The change. */
	@Field("change")
	private String change;

	/** The accountBalance. */
	@Field("accountBalance")
	private String accountBalance;

	/** The earning. */
	@Field("earning")
	private String earning;

	/** The income. */
	@Field("income")
	private String income;

	/** The overAllProfit. */
	@Field("overAllProfit")
	private String overAllProfit;

	/** The error */
	private String error;

}
