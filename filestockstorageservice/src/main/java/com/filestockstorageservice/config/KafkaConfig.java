package com.filestockstorageservice.config;

import java.util.HashMap;
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

import com.filestockstorageservice.model.StockResponse;

/**
 * The class KafkaConfig.
 * 
 * @author harsh.jain
 *
 */
@Configuration
public class KafkaConfig {

	/** The bootstrapServerName */
	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String bootstrapServerName;

	/**
	 * Create bean for produce the message for stock
	 * 
	 * @return ProducerFactory
	 */
	@Bean
	public ProducerFactory<String, StockResponse> producerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerName);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory(config);
	}

	/**
	 * Create bean for kafkaTemplate for the message for stock.
	 * 
	 * @return KafkaTemplate
	 */
	@Bean
	public KafkaTemplate<String, StockResponse> kafkaTemplate() {
		return new KafkaTemplate<String, StockResponse>(producerFactory());
	}

	/**
	 * Instantiates a new kafkaConfig.
	 */
	public KafkaConfig() {
		super();
	}

}
