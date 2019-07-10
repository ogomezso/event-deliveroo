package com.datahack.eventdeliveroo.order.infrastructure.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.datahack.eventdeliveroo.order.domain.model.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaConfig {

  private static final String SECURITY_PROTOCOL = "security.protocol";
  private final KafkaConfigProp kafkaConfigProp;

  public KafkaConfig(
      KafkaConfigProp kafkaConfigProp) {
    this.kafkaConfigProp = kafkaConfigProp;
  }

  @Bean
  public Map<String, Object> producerConfig() {
    Map<String, Object> props = new HashMap<>();

    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        kafkaConfigProp.getBootstrapServers());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    return props;
  }

  @Bean
  public ProducerFactory<String, Order> eventTrackerProducerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfig(), new StringSerializer(),
        new JsonSerializer<>());
  }

  @Bean
  public KafkaTemplate<String, Order> eventTrackerKafkaTemplate() {
    return new KafkaTemplate<>(eventTrackerProducerFactory());
  }

}
