package com.usermanagementservice.event.listner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.usermanagementservice.model.LiveStockDetails;

/**
 * The class KafkaConfig
 * 
 * @author harsh.jain
 *
 */
@Configuration
public class KafkaConfig {

	/**
	 * The brokerServerName.
	 */
	@Value(value = "${spring.broker.server.name}")
	private String brokerServerName;

	/**
	 * The producerFactory
	 * 
	 * @return ProducerFactory
	 */
	@Bean
	public ProducerFactory<String, List<LiveStockDetails>> producerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerServerName);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory(config);
	}

	/**
	 * The kafkaTemplate
	 * 
	 * @return KafkaTemplate
	 */
	@Bean
	public KafkaTemplate<String, List<LiveStockDetails>> kafkaTemplate() {
		return new KafkaTemplate<String, List<LiveStockDetails>>(producerFactory());
	}
}
