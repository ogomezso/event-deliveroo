package com.datahack.eventdeliveroo.order.infrastructure.mongo

import com.datahack.eventdeliveroo.order.domain.model.OrderState
import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument
import de.flapdoodle.embed.mongo.MongodExecutable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

@DataMongoTest
class OrderRepositoryTest extends Specification {

    @Autowired
    OrderRepository orderRepository

    private MongodExecutable mongodExecutable;

    def document = OrderDocument.builder()
            .documentId(UUID.randomUUID().toString())
            .orderId(UUID.randomUUID().toString())
            .orderState(OrderState.ORDERED)
            .build()

    def document2 = OrderDocument.builder()
            .documentId(UUID.randomUUID().toString())
            .orderId(UUID.randomUUID().toString())
            .orderState(OrderState.ORDERED)
            .build()

    def cleanup() {
        StepVerifier
                .create(orderRepository.deleteAll())
                .verifyComplete()
    }

    def "given correct document when save then mono of document returned"() {

        when:

        Mono<OrderDocument> result = orderRepository.save(document)

        then:

        StepVerifier.create(result)
                .expectNext(document)
                .verifyComplete()
    }

    def "given 2 different documents when save then 2 documents stored "() {

        when:

        Mono<OrderDocument> result = orderRepository.save(document)

        then:

        StepVerifier
                .create(result)
                .expectNext(document)
                .verifyComplete()

        when:

        Mono<OrderDocument> result2 = orderRepository.save(document2)

        then:

        StepVerifier
                .create(result2)
                .expectNext(document2)
                .verifyComplete()

    }


    def "given same document 2 times when save then just one saved and duplicate key exception returned"() {

        when:

        Mono<OrderDocument> result = orderRepository.save(document)

        then:

        StepVerifier
                .create(result)
                .expectNext(document)
                .verifyComplete()

        when:

        Mono<OrderDocument> result2 = orderRepository.save(document)

        then:

        StepVerifier
                .create(result2)
                .expectError(DuplicateKeyException)
    }

    def "given null document when save then error"() {

        when:

        orderRepository.save(null)

        then:

        thrown(IllegalArgumentException)
    }
}
