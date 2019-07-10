package com.datahack.eventdeliveroo.order.infrastructure.mongo

import com.datahack.eventdeliveroo.order.domain.model.OrderState
import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@DataMongoTest
class OrderRepositoryTest extends Specification {

    @Autowired
    OrderRepository orderRepository


    def cleanup() {
        StepVerifier
                .create(orderRepository.deleteAll())
                .verifyComplete()
    }

    def "given correct document when save then mono of document returned"() {

        given:

        OrderDocument document = OrderDocument.builder()
                .id(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .courierId(UUID.randomUUID().toString())
                .orderState(OrderState.ORDERED)
                .build()

        when:

        Publisher<OrderDocument> result = orderRepository.save(document)

        then:

        StepVerifier.create(result)
                .expectNext(document)
                .verifyComplete()
    }

    def "given 2 different documents when save then 2 documents stored "() {

        given:

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now()
        String formatDateTime = localDateTime.format(formatter)


        OrderDocument document = OrderDocument.builder()
                .id(UUID.randomUUID().toString())
                .date(LocalDateTime.parse(formatDateTime, formatter))
                .courierId(UUID.randomUUID().toString())
                .orderState(OrderState.ORDERED)
                .build()

        OrderDocument document2 = OrderDocument.builder()
                .id(UUID.randomUUID().toString())
                .date(LocalDateTime.parse(formatDateTime, formatter))
                .courierId(UUID.randomUUID().toString())
                .orderState(OrderState.ORDERED)
                .build()

        when:

        Publisher<OrderDocument> result = orderRepository.saveAll(Flux.just(document, document2))

        then:

        StepVerifier
                .create(result)
                .expectNext(document, document2)
                .verifyComplete()

    }


    def "given same document 2 times when save then just one saved and duplicate key exception returned"() {
        given:

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now()
        String formatDateTime = localDateTime.format(formatter)


        OrderDocument document = OrderDocument.builder()
                .id(UUID.randomUUID().toString())
                .date(LocalDateTime.parse(formatDateTime, formatter))
                .courierId(UUID.randomUUID().toString())
                .orderState(OrderState.ORDERED)
                .build()

        when:

        Publisher<OrderDocument> result = orderRepository.saveAll(Arrays.asList(document, document))

        then:

        StepVerifier
                .create(result)
                .expectNext(document)
                .expectError()
    }

    def "given null document when save then error"() {

        when:

        orderRepository.save(null)

        then:

        thrown(IllegalArgumentException)
    }
}
