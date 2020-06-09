package com.usermanagementservice.model;

import java.util.List;

import com.couchbase.client.java.repository.annotation.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class OrderHistoryResponse
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponse {

	@Field("id")
	private String id;

	/** The userId */
	@Field("userId")
	private String userId;

	/** The userName */
	@Field("userName")
	private String userName;

	/** The orderHistory */
	@Field("userOrderHistory")
	private List<UserOrderHistory> userOrderHistory;

	/** The count */
	private String count;

	/** The overAllProfit */
	@Field("overAllProfit")
	private String overAllProfit;
}