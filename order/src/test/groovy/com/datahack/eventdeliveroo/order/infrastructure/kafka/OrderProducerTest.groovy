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
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.test.rule.EmbeddedKafkaRule
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
class OrderProducerTest extends Specification {

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, "sender.t")

    static final String SENDER_TOPIC = "sender.t"

    @Autowired
    OrderProducer orderSender

    void setup() {
        System.setProperty("kafka.bootstrap-servers", embeddedKafka.getBrokersAsString())
        System.setProperty("kafka.groupId", "orders")
        embeddedKafka.before()
    }

    String consume() {
        Map<String, Object> consumerProperties =
                KafkaTestUtils.consumerProps("sender", "false",
                        embeddedKafka.getEmbeddedKafka())
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, String.class)
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class)
        consumerProperties.put(JsonDeserializer.TRUSTED_PACKAGES, "*")
        consumerProperties.put("auto.offset.reset", "earliest")
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "testGroup")

        String message = null
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties, new StringDeserializer(), new JsonDeserializer<>(String.class))
        consumer.subscribe(Collections.singleton(SENDER_TOPIC))

        while (message == null) {
            ConsumerRecords<String, String> records = consumer.poll(500)
            for (ConsumerRecord<String, String> record : records) {
                message = record.value()
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
                .orderId(UUID.randomUUID().toString())
                .orderState(OrderState.READY)
                .build()

        when:

        orderSender.send(order)


        then:

        ObjectMapper mapper = new ObjectMapper()

        String received = consume()

        order == mapper.readValue(received, Order.class)

    }
}
