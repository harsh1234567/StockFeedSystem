package com.usermanagementservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.model.User;

/**
 * The interface CommonUtils
 * 
 * @author harsh.jain
 *
 */
public interface CommonUtils {

	/** LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * getUniqueId
	 * 
	 * @param String symbolName
	 * 
	 * @param String symbolId
	 * 
	 */
	static String getUniqueId(String symbolName, String symbolId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_UNQIUE_KEY);
		StringBuilder uniqueId = new StringBuilder();
		uniqueId.append(symbolName).append(Constants.COLON_COLON).append(symbolId);
		return uniqueId.toString();
	}

	/**
	 * getStockId
	 * 
	 * @param String symbolName
	 * 
	 * @param String symbolId
	 * 
	 */
	static String getStockId(String symbolName, String symbolId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_STOCK_ID);
		StringBuilder stockKey = new StringBuilder(Constants.STOCK_PREFIX);
		stockKey.append(symbolName).append(Constants.COLON_COLON).append(symbolId);
		return stockKey.toString();
	}

	/**
	 * getStringArray
	 * 
	 * @param List<String> arr
	 * 
	 * @param String       Array
	 * 
	 */
	public static String[] getStringArray(List<String> arr) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_STRING_ARRAY_METHOD);
		Object[] objArr = arr.toArray();
		String[] str = Arrays.copyOf(objArr, objArr.length, String[].class);
		return str;
	}

	/**
	 * dateRangeWithIn
	 * 
	 * @param String timeStamp.
	 * 
	 * @param String startDate.
	 * 
	 * @param String endDate.
	 * 
	 * @param String Array
	 * 
	 */
	static boolean dateRangeWithIn(String timeStamp, String startDate, String endDate) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.DATE_RANGE_WITH_IN);
		try {
			Date startDate1 = new SimpleDateFormat(Constants.ORDER_DATE_PATTERN).parse(startDate);
			Date timeStamp1 = new SimpleDateFormat(Constants.ORDER_DATE_PATTERN).parse(timeStamp);
			Date endDate1 = new SimpleDateFormat(Constants.ORDER_DATE_PATTERN).parse(endDate);
			return timeStamp1.getTime() >= startDate1.getTime() && timeStamp1.getTime() <= endDate1.getTime();
		} catch (ParseException e) {
			LOGGER.error(LoggerConstants.ERROR_OCCURED_WHILE_DATE_COMPARSION + e);
		}
		return false;
	}

	/**
	 * Method to check null or empty string.
	 *
	 * @param str the str
	 * @return true, if successful
	 */
	public static boolean emptyString(String str) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.EMPTY_STRING_CHECK);
		if (str != null && Constants.BLANK_STRING.equals(str.trim())) {
			return true;
		} else
			return false;
	}

	/**
	 * Method collectionToStream
	 * @param <T>
	 *
	 * @paramCollection<OrderInfo> collection
	 * 
	 * @return Broker
	 * 
	 */
	static <T> Stream<T> collectionToStream(Collection<T> collection) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.COLLECTION_STREAM);
		return Optional.ofNullable(collection).map(Collection::parallelStream).orElseGet(Stream::empty);
	}

	/**
	 * Method portfolioUserId
	 *
	 * @param User user.
	 * 
	 * @return String
	 * 
	 */
	static String portfolioUserId(User user) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.PORFOLIO_USER_ID);
		StringBuilder getPortfolioUserId = new StringBuilder(Constants.PORTFOLIO_PERFIX);
		getPortfolioUserId.append(user.getUsername());
		getPortfolioUserId.append(Constants.COLON_COLON);
		getPortfolioUserId.append(user.getUserId());
		return getPortfolioUserId.toString();
	}

	/**
	 * Method OrderKey
	 *
	 * @param String UserId .
	 * 
	 * @param String symbol
	 * 
	 * @return String
	 * 
	 */
	static String OrderKey(String symbol, String symbolId, String userId) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.ORDER_KEY);
		StringBuilder getOrderKey = new StringBuilder(Constants.ORDER_PREFIX);
		getOrderKey.append(symbol).append(Constants.COLON_COLON).append(symbolId).append(Constants.COLON_COLON)
				.append(userId);
			return getOrderKey.toString();
	}
}
