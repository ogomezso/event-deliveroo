package com.datahack.eventdeliveroo.kitchen.infrastructure.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;


import com.datahack.eventdeliveroo.kitchen.domain.model.Order;

import reactor.core.publisher.EmitterProcessor;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {


  private final KafkaConfigProp kafkaConfigProp;

  @Autowired
  public KafkaConsumerConfig(
      KafkaConfigProp kafkaConfigProp) {
    this.kafkaConfigProp = kafkaConfigProp;
  }

  @Bean
  public Map<String, Object> consumerConfig() {
    Map<String, Object> props = new HashMap<>();
    // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProp.getBootstrapServers());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    // allows a pool of processes to divide the work of consuming and processing records
    props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigProp.getGroupId());
    return props;
  }

  @Bean
  public DefaultKafkaConsumerFactory<String, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
        consumerConfig(),
        new StringDeserializer(), new JsonDeserializer<>(String.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());

    return factory;
  }

  @Bean
  EmitterProcessor<Order> emitterProcessor() {
    return EmitterProcessor.create(false);
  }


}
