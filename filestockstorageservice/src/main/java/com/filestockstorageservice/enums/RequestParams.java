package com.filestockstorageservice.enums;

/**
 * The requestParams.
 * 
 * @author harsh.jain
 *
 */
public enum RequestParams {

	/** The file. */
	FILE("file");

	/** The val. */
	private String val;

	/**
	 * Instantiates a Request param .
	 *
	 * @param val the val
	 */
	RequestParams(final String val) {
		this.val = val;
	}

	/**
	 * Gets the val.
	 *
	 * @return the val
	 */
	public String getVal() {
		return this.val;
	}

}
