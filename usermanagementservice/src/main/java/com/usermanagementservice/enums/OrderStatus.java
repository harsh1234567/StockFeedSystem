package com.usermanagementservice.enums;

/**
 * The enum OrderStatus
 * 
 * @author harsh.jain
 *
 */
public enum OrderStatus {

	BUYING("buy"), PENDING("pending"), COMPLETED("completed"), SELL("sell");

	private String val;

	/**
	 * Instantiates a OrderStatus.
	 *
	 * @param val the val
	 */
	OrderStatus(final String val) {
		this.val = val;
	}

	/**
	 * The getVal
	 * 
	 * @return String
	 */
	public String getVal() {
		return val;
	}

}
