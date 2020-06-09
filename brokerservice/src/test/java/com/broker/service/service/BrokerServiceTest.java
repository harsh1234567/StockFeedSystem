package com.broker.service.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.broker.service.model.Broker;

/**
 * The BrokerServiceTest
 * 
 * @author harsh.jain
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrokerServiceTest {

	/**
	 * The BrokerService.
	 * 
	 */
	@InjectMocks
	BrokerService brokerService;

	/**
	 * The brokerChargesTest.
	 * 
	 */
	@Test
	public void brokerChargesTest() {
		Broker broker = brokerService.brokerCharges("day");
		Assert.assertNotNull(broker.getBrokekageType());
	}
}
