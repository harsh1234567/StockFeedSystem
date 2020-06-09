package com.filestockstorageservice.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.filestockstorageservice.constants.Constant;
import com.filestockstorageservice.model.ProcessStocks;
import com.filestockstorageservice.util.CommonUtil;
import com.filestockstorageservice.util.DateUtils;

/**
 * The interface CommonUtil.
 * 
 * @author harsh.jain
 *
 */
public class CommonUtilTest {
	
	/** The LOGGER. */
	Logger LOGGER = LoggerFactory.getLogger(CommonUtilTest.class);

	/**
	 * The getUnqiueIdTest
	 * */
	@Test
	public void getUnqiueIdTest() {

		String symbolId = "2";
		String symbolName = "HEROMOTOCO";
		Assert.assertEquals(symbolName + "::" + symbolId, CommonUtil.getUniqueId(symbolName, symbolId));
	}
	
    @Test
	public void processStocksTest() {
		ProcessStocks process=CommonUtil.processStocks(process());
		Assert.assertEquals("TATAMOTOR", process.getSymbolName());
		
	}
	
	private String[] process() {
		String process[]= {
				"3","TATAMOTOR","100","100","200","100","100","100"
		};
		return process;
	}


	/**
	 * 
	 * Test format date time.
	 */
	@Test
	public void testFormatDateTime() {
		assertNotNull(DateUtils.formatDateTime(null));
	}

	public CommonUtilTest() {
		super();
	}

}