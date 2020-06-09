package com.usermanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class OrderState.
 * 
 * @author harsh.jain
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderState {

	/** The status */
	private String status;

	/** The lastUpdate */
	private String lastUpdate;

}
