package com.filestockstorageservice.util;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.filestockstorageservice.constants.Constant;
import com.filestockstorageservice.constants.LoggerConstants;
import com.filestockstorageservice.model.ProcessStocks;

/**
 * The interface commonUtil.
 * 
 * @author harsh.jain
 *
 */
public interface CommonUtil {

	/** The Constant LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * The String to double
	 * 
	 * @param string
	 * 
	 * @return double
	 */
	static String validString(String string) {
		LOGGER.info(LoggerConstants.CONVERT_TO_VALID_STRING);
		return string.replace(Constant.REMOVE_QUOTES, Constant.EMPTY).replace(Constant.COMMA, Constant.EMPTY);
	}

	/**
	 * The Timestamp create
	 *
	 * 
	 * @return String
	 */

	static String currentTimeStamp() {
		Instant currTimeStamp = Instant.now();
		LOGGER.info(LoggerConstants.TIMESTAMP);
		return currTimeStamp.toString();
	}

	/**
	 * The Process and get processStocks as list
	 * 
	 * @param getData
	 *
	 * @return StockRecords.
	 *
	 * 
	 * @return String
	 */
	public static ProcessStocks processStocks(String process[]) {
		ProcessStocks processStocks = new ProcessStocks();
		processStocks.setSymbolName(process[1].replace(Constant.REMOVE_QUOTES, Constant.EMPTY));
		processStocks.setSymbolId(process[0]);
		processStocks.setOpenPrice(CommonUtil.validString(process[2]));
		processStocks.setHighestPrice(CommonUtil.validString(process[3]));
		processStocks.setLow(CommonUtil.validString(process[4]));
		processStocks.setLastPrice(CommonUtil.validString(process[5]));
		processStocks.setCurrentPrice(CommonUtil.validString(process[6]));
		processStocks.setTimeStamp(CommonUtil.currentTimeStamp());
		if (!ObjectUtils.isEmpty(CommonUtil.validString(process[7]))) {
			processStocks.setQuantity(CommonUtil.validString(process[7]));
		} else {
			processStocks.setQuantity("0");

		}
		return processStocks;
	}

	/**
	 * To find getuniqueId
	 * 
	 * @param symbolName
	 * 
	 * @param sumbolId
	 * 
	 * @return String
	 */
	static String getUniqueId(String symbolName, String symbolId) {
		LOGGER.info(LoggerConstants.GET_UNIQUE_ID);
		StringBuilder uniqueId = new StringBuilder();
		uniqueId.append(symbolName);
		uniqueId.append(Constant.COLON_COLON);
		uniqueId.append(symbolId);
		return uniqueId.toString();
	}

}
