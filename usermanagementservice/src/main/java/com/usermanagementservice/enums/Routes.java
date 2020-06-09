package com.usermanagementservice.enums;

/**
 * The Enum HandlerType.
 */
public enum Routes {

	/** The Portfolio Api controller Route */
	PORTFOLIO_API("/portfolio"),
	/** The Constant BUY_API. */
	POST_BUY_API("/buy"),

	/** The Constant SELL_API. */
	DELETE_SELL_API("/sell"),

	/** The Constant GET_ALL_LATEST_STOCK_API. */
	GET_ALL_LATEST_STOCK_API("/getAllLatestStockPrice"),

	/** The Constant GET_LATEST_STOCK_API. */
	GET_LATEST_STOCK_API("/getLatestStockPrice"),

	/** The Constant STOCK_API. */
	GET_STOCK_API("/stock"),

	/** The Constant USER_API. */
	USER_API("/user"),

	/** The Constant CREATE_USER_API. */
	POST_CREATE_USER_AP("/createUser"),

	/** The Constant CREATE_USER_API. */
	GET_USER_PORTFOLIO_API("/getUserPortfolio"),

	/** The Constant CREATE_USER_API. */
	GET_ALL_USER_PORTFOLIO_API("/getAllUserPortfolio"),

	/** The Constant CREATE_USER_API. */
	GET_USER_ORDER_HISTORY("/getUserOrderHistory"),

	/** The Constant CREATE_USER_API. */
	GET_ALL_USER_ORDER_HISTORY("/getAllUserOrderHistory"),

	/** The Constant CREATE_USER_API. */
	GET_PORTFOLIO_API("/getPortfolio"),

	/** The Constant CREATE_USER_API. */
	GET_UPDATE_USER("/updateUser");

	private final String url;

	Routes(String url) {
		this.url = url;

	}

	public String getVal() {
		return url;
	}

	/**
	 * Value.
	 *
	 * @return the string
	 */

	public String value() {
		return this.url;
	}
}