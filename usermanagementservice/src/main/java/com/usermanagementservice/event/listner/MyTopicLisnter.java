package com.usermanagementservice.event.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;
import com.usermanagementservice.model.LiveFeeds;
import com.usermanagementservice.service.StockService;
import com.usermanagementservice.service.UserPortfolioService;

/**
 * The class MyTopicLisnter.
 * 
 * @author harsh.jain
 *
 */
@Component
public class MyTopicLisnter {
	/**
	 * The userPortfolioService.
	 */
	@Autowired
	private UserPortfolioService userPortfolioService;

	@Autowired
	private StockService stockService;

	/**
	 * The logger
	 */
	private final Logger logger = LoggerFactory.getLogger(MyTopicLisnter.class);

	/**
	 * The getJsonFromTopic.
	 * 
	 * @param LiveFeeds liveFeeds.
	 * 
	 **/
	@KafkaListener(groupId = Constants.LIVE_STOCKLISNTER_GROUP0, topics = Constants.LIVE_STOCK_LISTNER, containerFactory = Constants.KAFKA_LISTENER_CONTAINER_FACTORY)
	public void getJsonFromTopic(LiveFeeds liveFeeds) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.GET_JSON_FROM_TOPIC_METHOD);
		userPortfolioService.updatePortfolio(liveFeeds);
		stockService.saveLatestFeed(liveFeeds);
	}

	/**
	 * The instance MyTopicLisnter.
	 * 
	 * 
	 **/
	public MyTopicLisnter() {
		super();
	}

}
