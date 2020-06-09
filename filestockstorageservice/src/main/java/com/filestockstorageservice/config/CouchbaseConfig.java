package com.filestockstorageservice.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

/**
 * The Class CouchbaseConfig
 * 
 * @author harsh.jain
 *
 */
@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

	/** The host */
	@Value("${couchbase.path}")
	private String host;

	/** The username */
	@Value("${couchbase.username}")
	private String username;

	/** The bucketName */
	@Value("${couchbase.bucketName}")
	private String bucketName;

	/** The bucketPassword */
	@Value("${couchbase.bucketPassword}")
	private String bucketPassword;

	/**
	 * @return String
	 * 
	 */
	@Override
	protected String getUsername() {
		return username;
	}

	/**
	 * @return List
	 * 
	 */
	@Override
	protected List<String> getBootstrapHosts() {
		return Arrays.asList(host);
	}

	/**
	 * @return String
	 * 
	 */
	@Override
	protected String getBucketName() {
		return bucketName;
	}

	/**
	 * @return String
	 * 
	 */
	@Override
	protected String getBucketPassword() {
		return bucketPassword;
	}

}
