package com.broker.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class Broker.
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Broker {

	/** The brokerId */
	private String brokerId;

	/** The brokekageType */
	private String brokekageType;

	/** The brokerCharges */
	private String brokerCharges;
}
