
package com.usermanagementservice.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.usermanagementservice.constants.LoggerConstants;

/**
 * The Class DateUtils.
 * 
 * @author harsh.jain
 *
 */
public final class DateUtils {

	/** The ISO_9601_FORMAT constant. */
	private static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'+00:00'";

	/** The LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * Instantiates a new date utils.
	 */

	private DateUtils() {
		super();
	}

	/**
	 * Formats the input local date time into the given pattern.
	 *
	 * @param dateTime the date time to format
	 * @return the optional formatted date time string, empty optional if date time
	 *         or pattern null
	 */
	public static Optional<String> formatDateTime(final LocalDateTime dateTime) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.FORMAT_DATE_TIME);
		Optional<String> formattedDateTime = Optional.empty();
		if (dateTime != null) {
			OffsetDateTime offsetDateTime = dateTime.atOffset(ZoneOffset.UTC);
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601_FORMAT);
			formattedDateTime = Optional.ofNullable(offsetDateTime.format(formatter));
		}
		return formattedDateTime;
	}

	/**
	 * getTimeStamp
	 *
	 * @return String
	 */
	public static String getTimeStamp() {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.get_TIMESTAMP);
		return DateUtils.formatDateTime(LocalDateTime.now()).get();
	}
	public static String currentTimeStamp() {
		Instant currTimeStamp = Instant.now();
		LOGGER.info(LoggerConstants.TIMESTAMP);
		return currTimeStamp.toString();
	}

}
