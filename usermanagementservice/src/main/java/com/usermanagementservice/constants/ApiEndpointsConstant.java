package com.usermanagementservice.constants;

/**
 * The class ControllerConstants
 * 
 * @author harsh.jain
 *
 */
public class ApiEndpointsConstant {

	/** The Constant SAVE_INFO. */
	public static final String PORTFOLIO_API = "/portfolio";

	/** The Constant BUY_API. */
	public static final String BUY_API = "/buy";

	/** The Constant SELL_API. */
	public static final String SELL_API = "/sell";

	/** The Constant GET_ALL_LATEST_STOCK_API. */
	public static final String GET_ALL_LATEST_STOCK_API = "/getAllLatestStockPrice";

	/** The Constant GET_LATEST_STOCK_API. */
	public static final String GET_LATEST_STOCK_API = "/getLatestStockPrice";

	/** The Constant STOCK_API. */
	public static final String STOCK_API = "/stock";

	/** The Constant USER_API. */
	public static final String USER_API = "/user";

	/** The Constant CREATE_USER_API. */
	public static final String CREATE_USER_API = "/createUser";

	/** The Constant GET_USER_PORTFOLIO_API. */
	public static final String GET_USER_PORTFOLIO_API = "/getUserPortfolio";

	/** The Constant GET_USER_PORTFOLIO_API. */
	public static final String GET_ALL_USER_PORTFOLIO_API = "/getAllUserPortfolio";

	/** The Constant GET_USER_ORDER_HISTORY. */
	public static final String GET_USER_ORDER_HISTORY = "/getUserOrderHistory";

	/** The Constant GET_ALL_USER_ORDER_HISTORY. */
	public static final String GET_ALL_USER_ORDER_HISTORY = "/getAllUserOrderHistory";

	/** The Constant GET_PORTFOLIO_API. */
	public static final String GET_PORTFOLIO_API = "/getPortfolio";

	/** The Constant GET_UPDATE_USER. */
	public static final String GET_UPDATE_USER = "/updateUser";

	/** The Constant GET_USER_PRE_SELL_ORDER_API. */
	public static final String GET_USER_PRE_SELL_ORDER_API = "/getUserPreSellOrder";

	/** The Constant GET_TOKEN_API. */
	public static final String GET_TOKEN_API = "/user/getToken";

	/** The Constant GET_CREATE_USER_API. */

	public static final String POST_CREATE_USER_API = "/user/createUser";

	/** The Constant GET_ACCESS_TOKEN_API. */
	public static final String GET_ACCESS_TOKEN_API = "/getToken";

	/** The Constant BROKER_GET_CALL_API. */
	public static final String BROKER_GET_CALL_API = "/broker/getBrokerInfo?brokerType=";

	/** The default constructor. */
	private ApiEndpointsConstant() {
		super();
	}
}
