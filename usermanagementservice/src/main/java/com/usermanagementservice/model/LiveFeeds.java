package com.usermanagementservice.model;

import java.io.Serializable;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class LiveFeeds.
 * 
 * @author harsh.jain
 *
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveFeeds implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** The metaId */
	@Id
	private String metaId;

	/** The symbol */
	@JsonProperty("symbol")
	@Field("symbol")
	private String symbol;

	/** The symbolId */
	@Field("symbolId")
	@JsonProperty("symbolId")
	private String symbolId;

	/** The latestFeed */
	@Field("latestFeed")
	@JsonProperty("latestFeed")
	private LatestFeed latestFeed;

}
