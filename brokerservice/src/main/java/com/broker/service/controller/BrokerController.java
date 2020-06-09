package com.broker.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.broker.service.constants.BrokerConstants;
import com.broker.service.constants.LoggerConstants;
import com.broker.service.model.Broker;
import com.broker.service.service.BrokerService;

/**
 * The BrokerController.
 * 
 * @author harsh.jain
 *
 */
@RestController
@RequestMapping(value = BrokerConstants.BROKER_API)
public class BrokerController {

	/** The LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(BrokerService.class);

	/**
	 * The BrokerService.
	 */
	@Autowired
	BrokerService brokerService;

	/**
	 * The getbrokerInfo.
	 * 
	 * @param brokerType.
	 * 
	 * @return Broker.
	 */
	@GetMapping(BrokerConstants.GET_BROKER_INFO_API)
	public Broker getbrokerInfo(@RequestParam(BrokerConstants.BROKER_TYPE) String brokerType) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_BROKER_INFO);
		return brokerService.brokerCharges(brokerType);
	}

}
