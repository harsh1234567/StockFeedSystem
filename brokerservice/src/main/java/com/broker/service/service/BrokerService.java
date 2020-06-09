package com.broker.service.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.broker.service.constants.BrokerConstants;
import com.broker.service.constants.LoggerConstants;
import com.broker.service.model.Broker;

/**
 * The class BrokerService
 * 
 * @author harsh.jain
 *
 */
@Service
public class BrokerService {

	/** The brokerId */
	@Value("${brokerId}")
	private String brokerId;

	/** The dayCharges */
	@Value("${dayCharges}")
	private String dayCharges;

	/** The lifeTimeCharges */
	@Value("${lifeTimeCharges}")
	private String lifeTimeCharges;

	/** The brokers */
	private List<Broker> brokers;

	/** The LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(BrokerService.class);

	/**
	 * The brokerCharges.
	 * 
	 * @Param brokageType.
	 * 
	 * @return Broker.
	 */
	public Broker brokerCharges(String brokageType) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.BROKER_CHARGES_METHOD);
		brokers = Arrays.asList(new Broker(brokerId, BrokerConstants.DAY, dayCharges),
				new Broker(brokerId, BrokerConstants.LIFE_TIME, lifeTimeCharges));
		Broker br = brokers.stream().filter(broker -> broker.getBrokekageType().equals(brokageType)).findFirst().get();
		return br;
	}

	/** The constructor */
	public BrokerService() {
		super();
	}
}
