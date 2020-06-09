package com.usermanagementservice.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class OrderHistory
 * 
 * @author harsh.jain
 *
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory implements Serializable {
	/**
	 * The serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The id */
	@Id
	@Field("id")
	private String id;

	/** The userId */
	@Field("userId")
	private String userId;

	/** The orderId */
	@Field("orderId")
	private String orderId;

	/** The userName */
	@Field("userName")
	private String userName;
	/** The side */
	@Field("side")
	private String side;

	/** The symbolId */
	@Field("symbolId")
	private String symbolId;

	/** The symbolName */
	@Field("symbolName")
	private String symbolName;

	/** The buyPrice */
	@Field("buyPrice")
	private String buyPrice;

	/** The totalLoss */
	@Field("totalLoss")
	private String totalLoss;

	/** The totalProfit */
	@Field("totalProfit")
	private String totalProfit;

	/** The sellPrice */
	@Field("sellPrice")
	private String sellPrice;

	/** The duration */
	@Field("duration")
	private String duration;

	/** The quantity */
	@Field("quantity")
	private String quantity;

	/** The timeStamp */
	@Field("timeStamp")
	private String timeStamp;

	/** The orderHistory */
	private transient List<OrderHistory> orderHistory;

}
