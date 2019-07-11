package com.datahack.eventdeliveroo.order.infrastructure.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfigProp {

  private String bootstrapServers;
  private String groupId;
  private String securityProtocol;
  private String topic;
}
