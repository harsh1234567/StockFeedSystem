package com.usermanagementservice.event.listner;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.model.LiveFeeds;

/**
 * The class ConsumerConfiguration
 * 
 * @author harsh.jain
 *
 */
@Configuration
@EnableKafka
public class ConsumerConfiguration {

	/**
	 * The KAFKA_BROKER
	 */
	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String KAFKA_BROKER;

	/**
	 * new instanc create for deserializer.
	 */
	JsonDeserializer<LiveFeeds> deserializer = new JsonDeserializer(LiveFeeds.class);
	{
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages(Constants.ASTRIK);
		deserializer.setUseTypeMapperForKey(true);
	}

	/**
	 * The consumerFactory
	 * 
	 * @return ConsumerFactory.
	 * 
	 */
	@Bean
	public ConsumerFactory<String, LiveFeeds> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), deserializer);
	}

	/**
	 * The consumerConfigurations
	 * 
	 * @return Map.
	 * 
	 */
	@Bean
	public Map<String, Object> consumerConfigurations() {
		Map<String, Object> configurations = new HashMap<>();
		configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
		configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		configurations.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.LIVE_STOCKLISNTER_GROUP0);
		return configurations;
	}

	/**
	 * The kafkaListenerContainerFactory
	 * 
	 * @return ConcurrentKafkaListenerContainerFactory.
	 * 
	 */
	@Bean
	ConcurrentKafkaListenerContainerFactory<String, LiveFeeds> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, LiveFeeds> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
