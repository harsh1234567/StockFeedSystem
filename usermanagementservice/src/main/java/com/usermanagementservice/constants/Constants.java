package com.usermanagementservice.constants;

import java.text.SimpleDateFormat;

/**
 * The class Constants
 * 
 * @author harsh.jain
 *
 */
public class Constants {

	/** The constant ORDER_PREFIX */
	public static final String ORDER_PREFIX = "ORDERID::";

	/** The constant LIVE_STOCK_LISTNER */
	public static final String LIVE_STOCK_LISTNER = "liveStockListner";

	/** The constant LIVE_STOCKLISNTER_GROUP0 */
	public static final String LIVE_STOCKLISNTER_GROUP0 = "liveStockListner-0";

	/** The constant KAFKA_LISTENER_CONTAINER_FACTORY */
	public static final String KAFKA_LISTENER_CONTAINER_FACTORY = "kafkaListenerContainerFactory";

	/** The constant SYMBOL_NAME */
	public static final String SYMBOL_NAME = "symbolName";

	/** The Constant COLON_COLON. */
	public static final String COLON_COLON = "::";

	/** The Constant USER_ID_SYMBOL_ID_REQUIRED. */
	public static final String USER_ID_SYMBOL_ID_REQUIRED = "symboId and userId is required";

	/** The Constant INCORRECT_HEADER. */
	public static final String INCORRECT_HEADER = "Header is incorrect";

	/** The Constant EMPTY. */
	public static final String EMPTY = "";

	/** The Constant INCORRECT_HEADER. */
	public static final String REQUEST_QUANTITY_PRICE = "quantity and price add only one of them !!";

	/** The Constant CONFLICT_REQUEST. */
	public static final String CONFLICT_REQUEST = "Conflict";
	/** The Constant BAD_REQUEST. */
	public static final String BAD_REQUEST = "Bad Request";

	/** The Constant OBJECT_NOT_FOUND_ERROR. */
	public static final String OBJECT_NOT_FOUND_ERROR = "Object not found";

	/** THE Consants INTERNAL_SERVER_ERROR */
	public static final String INTERNAL_SERVER_ERROR = "Internal server error ";

	/** The Constant UNPROCESSABLE_ENTITY. */
	public static final String UNPROCESSABLE_ENTITY = "Unprocessable Entity";

	/** The Constant LATEST. */
	public static final String LATEST = "LATEST::";

	/** The Constant HISTORY. */
	public static final String HISTORY = "HISTORY::";

	/** The Constant dateFormat. */
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	/** The Constant USER_PREFIX. */
	public static final String USER_PREFIX = "USER::";

	/** The Constant BLANK_STRING. */
	public static final String BLANK_STRING = "";

	/** The Constant ERROR_USER_EXIST. */
	public static final String ERROR_USER_EXIST = "User already exist with given userId";

	/** The Constant BLANK_EMAIL_ERROR. */
	public static final String BLANK_EMAIL_ERROR = "Please provide email";

	/** The Constant BLANK_PASSWORD_ERROR. */
	public static final String BLANK_PASSWORD_ERROR = "Password must be non empty string";

	/** The Constant STOCK_PREFIX. */
	public static final String STOCK_PREFIX = "STOCK::";

	/** The Constant TRANSACTION_PREFIX. */
	public static final String TRANSACTION_PREFIX = "TRANSACTION::";

	/** The Constant PURCHASE_PREFIX. */
	public static final String PURCHASE_PREFIX = "PURCHASE::";

	public static final String BLANK_USER_NAME_ERROR = "please provide the user name ";

	public static final String BLANK_USER_ID_ERROR = "please provide the user id";

	/** The Constant PORTFOLIO_PERFIX. */
	public static final String PORTFOLIO_PERFIX = "PORTFOLIO::";

	/** The Constant SYMBOL. */
	public static final String SYMBOL = "symbol";

	/** The Constant SYMBOL_ID. */
	public static final String SYMBOL_ID = "symbolId";

	/** The Constant SYMBOL_ID. */
	public static final String LATEST_FEED = "latestFeed";

	/** The Constant INVALID_ADMIN. */
	public static final String INVALID_ADMIN = "this is not admin user!!";

	/** The Constant NO_USER. */
	public static final String NO_USER = "no user found";

	/** The Constant PERCENTAGE. */
	public static final String PERCENTAGE = "%";

	/** The Constant USER_NAME. */
	public static final String USER_NAME = "userName";

	/** The Constant HUNDRED. */
	public static final String HUNDRED = "100";

	/** The Constant CHANGE. */
	public static final String CHANGE = "change";

	/** The Constant INVESTMENT. */
	public static final String INVESTMENT = "investment";

	/** The Constant EARNING. */
	public static final String EARNING = "earning";

	/** The Constant ZERO_DOUBLE_VALUE. */
	public static final String ZERO_DOUBLE_VALUE = "0.0";

	/** The Constant INCOME. */
	public static final String INCOME = "income";

	/** The Constant ORDER_INFO. */
	public static final String ORDER_INFO = "orderInfo";

	/** The Constant SELL_ERROR. */
	public static final String SELL_ERROR = "you don't have  stock for sell with this id !\"";

	/** The Constant USER_ID. */
	public static final String USER_ID = "userId";

	/** The Constant ERROR_QUANTITY_PRICE. */
	public static final String ERROR_QUANTITY_PRICE = "error while quantity and price both are present!";

	/** The Constant EMPTY_REQUEST_PAYLOAD. */
	public static final String EMPTY_REQUEST_PAYLOAD = "empty request payload";

	/** The Constant METHOD_INVOKE. */
	public static final String METHOD_INVOKE = "Method invoke {}";

	/** The Constant BUY. */
	public static final String BUY = "Buy";

	/** The Constant ERROR_WHILE_UPDATE_PORTFOLIO. */
	public static final String ERROR_WHILE_UPDATE_PORTFOLIO = "error occur while fetching the user portfolio ";

	/** The Constant ERROR_OCCURING_SELL. */
	public static final String ERROR_OCCURING_SELL = "error occured while sell information retriving";

	/** The Constant INVALID_USER. */
	public static final String INVALID_USER = "invalid user!!";

	/** The Constant INVALID_USER_FOR_SELL. */
	public static final String INVALID_USER_FOR_SELL = "invalid User you can't sell the stock";

	/** The Constant ADMIN_ID. */
	public static final String ADMIN_ID = "adminId";

	/** The Constant ASTRIK. */
	public static final String ASTRIK = "*";

	/** The Constant ASTRIK_ASTRIK. */
	public static final String ASTRIK_ASTRIK = "/**";

	/** The Constant SAVE_INTO_UMS. */
	public static final String SAVE_INTO_UMS = "save data in ums.";

	/** The Constant CONSUME_INTO_CACHE. */
	public static final String CONSUME_INTO_CACHE = "consume into cache";

	/** The Constant DATE_PATTERN. */
	public static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

	/** The Constant NULL_POINTER. */
	public static final String NULL_POINTER = "NULL_POINTER";

	/** The Constant STOCK_NOT_FOUND. */
	public static final String STOCK_NOT_FOUND = "STOCK_NOT_FOUND";

	/** The Constant SELL. */
	public static final String SELL = "Sell";

	/** The Constant SORDERHISTORY_PREFIXELL. */
	public static final Object ORDERHISTORY_PREFIX = "ORDERHISTORY::USER::";

	/** The Constant ORDER_DATE_PATTERN. */
	public static final String ORDER_DATE_PATTERN = "uuuu-MM-dd";

	/** The Constant AVAILABLE_QUANTITY_NOT_FOUND. */
	public static final String AVAILABLE_QUANTITY_NOT_FOUND = "available quantity is lesser than buy quantity";

	/** The Constant USER_NOT_FOUND. */
	public static final String USER_NOT_FOUND = "user not found";

	/** The Constant USER_ALREADY_EXIST. */
	public static final String USER_ALREADY_EXIST = "User Already exist";

	/** The Constant TOTAL_PROFIT. */
	public static final String TOTAL_PROFIT = "totalProfit";

	/** The Constant TOTAL_LOSS. */
	public static final String TOTAL_LOSS = "totalLoss";

	/** The Constant YOUR_CURRENT_BALANCE_IS_LOW. */
	public static final String YOUR_CURRENT_BALANCE_IS_LOW = "your current balance is less";

	/** The Constant SELL_ORDER_NOT_FOUND. */
	public static final String SELL_ORDER_NOT_FOUND = "sell order not found";

	/** The Constant NO_STOCK_FOUND_IN_PORTFOLIO. */
	public static final String NO_STOCK_FOUND_IN_PORTFOLIO = "no stocks foud in portfolio!";

	/** The Constant ERROR_WHILE_CREATING_USER. */
	public static final String ERROR_WHILE_CREATING_USER = "error occured while update the user info";

	/** The Constant USER. */
	public static final String USER = "USER";

	/** The Constant USER. */
	public static final String ADMIN = "ADMIN";

	/** The Constant ZERO. */
	public static final String ZERO = "0";

	/** The Constant USER_NAME_USER_ID_ERROR. */
	public static final String USER_NAME_USER_ID_ERROR = "user name or userId should not be empty";

	/** The Constant ERROR_OCCURING_WHILE_UPDATE_USER. */
	public static final String ERROR_OCCURING_WHILE_UPDATE_USER = "error occured while update the user info";

	/** The Constant ORDER_HISTORY. */
	public static final String ORDER_HISTORY = "orderHistory";

	/** The Constant USER_ORDER_NOT_FOUND. */
	public static final String USER_ORDER_NOT_FOUND = "User order not found!!";

	/** The Constant QUANTITY. */
	public static final String QUANTITY = "quantity";

	/** The Constant PRE_SELL. */
	public static final String PRE_SELL = "PRESELL";

	/** The Constant SYMBOL_NOT_FOUND. */
	public static final String SYMBOL_NOT_FOUND = "symbol not found!";

	/** The Constant SYMBOL_LIST_NOT_FOUND. */
	public static final String SYMBOL_LIST_NOT_FOUND = "symbol list not found";

	/** The Constant PASSCODEKEY. */
	public static final String PASSCODEKEY = "PBKDF2WithHmacSHA512";

	/** The Constant ROLE. */
	public static final String ROLE = "role";

	/** The Constant BEARER. */
	public static final String BEARER = "Bearer ";

	/** The Constant UNSUPPORTED_OPERATION_EXCEPTION. */
	public static final String UNSUPPORTED_OPERATION_EXCEPTION = "Not supported yet.";

	/** The Constant ROLE_ADMIN_USER. */
	public static final String ROLE_ADMIN_USER = "hasRole('USER') or hasRole('ADMIN')";

	/** The Constant ROLE_USER. */
	public static final String ROLE_USER = "hasRole('USER')";

	/** The Constant ROLE_ADMIN. */
	public static final String ROLE_ADMIN = "hasRole('ADMIN')";

	/** The Constant NUMERIC_PATTERN. */
	public static final String NUMERIC_PATTERN = "^[0-9]*$";

	/** The Constant BROKER_NOT_FOUND. */
	public static final String BROKER_NOT_FOUND = "broker service is not avaiable !!";

	/** The Constant BROKER_DETAILS_NOT_FOUND. */
	public static final String BROKER_DETAILS_NOT_FOUND = "broker details not found";

	/** The Constant INVALID_STOCK. */
	public static final String INVALID_STOCK = "invalid stock!";

	/** The Constant INVALID_STOCK. */
	public static final String DONOT_HAVE_QUANITY = "you don't have ";

	/** The Constant FEED_ERROR_SELL_ORDER. */
	public static final String FEED_ERROR_SELL_ORDER = "error while fetching the sellOrder()";

	/** The Constant INVALID_NUMERICE. */
	public static final String INVALID_NUMERICE = "invalid numerice value";

	/** The Constant ACCOUNT_BALANCE. */
	public static final String ACCOUNT_BALANCE = "accountBalance";

	/** The Constant OVER_ALL_PROFIT. */
	public static final String OVER_ALL_PROFIT = "overAllProfit";

	/** The Constant USER_PORTFOLIO_EXCEPTION. */
	public static final String USER_PORTFOLIO_EXCEPTION = "user portfolio Exception";

	/** The Constant USERNAME. */
	public static String USERNAME = "userName";

	/** The default constructor. */
	private Constants() {
		super();
	}

}
