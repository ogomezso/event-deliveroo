package com.datahack.eventdeliveroo.order.infrastructure.kafka

import com.datahack.eventdeliveroo.order.domain.model.Order
import com.datahack.eventdeliveroo.order.domain.model.OrderState
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.rule.EmbeddedKafkaRule
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

import java.time.LocalDateTime

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
class OrderSenderTest extends Specification {

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, "sender.t")

    static final String SENDER_TOPIC = "sender.t"

    @Autowired
    OrderSender orderSender

    void setup() {
        System.setProperty("kafka.bootstrap-servers", embeddedKafka.getBrokersAsString());
        embeddedKafka.before()
    }

    Object consume(String key) {
        Map<String, Object> consumerProperties =
                KafkaTestUtils.consumerProps("sender", "false",
                        embeddedKafka.getEmbeddedKafka())
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
        consumerProperties.put("auto.offset.reset", "earliest")

        Object message = null;
        KafkaConsumer<String, Order> consumer = new KafkaConsumer<>(consumerProperties)
        consumer.subscribe(Collections.singleton(SENDER_TOPIC))

        while (message == null) {
            ConsumerRecords<String, Order> records = consumer.poll(500);
            for (ConsumerRecord<String, Order> record : records) {

                if (key == null || record.key().equals(key)) {
                    message = record.value()
                }
            }
            consumer.commitSync()
        }
        consumer.close()
        return message
    }

    @Test
    def "Send"() {

        given:

        def order = Order.builder()
                .id(UUID.randomUUID().toString())
                .courierId(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .orderState(OrderState.READY)
                .build()

        when:

        orderSender.send(order)


        then:

        ObjectMapper mapper = new ObjectMapper()

        Object received = consume(order.id)

        received == mapper.writeValueAsString(order)

    }
}
