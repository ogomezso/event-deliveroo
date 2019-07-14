package com.datahack.eventdeliveroo.order.infrastructure.mongo

import com.datahack.eventdeliveroo.order.domain.model.Order
import com.datahack.eventdeliveroo.order.domain.model.OrderState
import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

class OrderDasTest extends Specification {

    def order = Order.builder()
            .orderId(UUID.randomUUID().toString())
            .orderState(OrderState.READY)
            .build()

    def order2 = Order.builder()
            .orderId(order.getOrderId())
            .orderState(OrderState.DELIVERED)
            .build()
    def orderDocument = OrderDocument.builder()
            .orderId(order.getOrderId())
            .orderState(order.getOrderState())
            .build()

    def orderDocument2 = OrderDocument.builder()
            .orderId(order2.getOrderId())
            .orderState(order2.getOrderState())
            .build()


    def "given Order domain object when PersistOrder then return Object Domain persisted"() {

        given:

        def stubbedOrderMongoAdapter = Stub(OrderMongoAdapter) {
            domain2Document(order) >> orderDocument
            document2Domain(_) >> order
        }

        def stubbedOrderRepository = Stub(OrderRepository) {
            save(orderDocument) >> Mono.just(orderDocument)
        }

        OrderDas orderDas = new OrderDas(stubbedOrderMongoAdapter, stubbedOrderRepository)

        when:

        Mono<Order> result = orderDas.saveOrder(order)

        then:

        StepVerifier.create(result)
                .expectNext(order)
                .verifyComplete()
    }


    def "when PersistOrder throw exception then Exception thrown"() {

        given:

        def stubbedOrderMongoAdapter = Stub(OrderMongoAdapter) {
            domain2Document(order) >> orderDocument
        }

        def stubbedOrderRepository = Stub(OrderRepository) {
            save(_) >> { throw new Exception("Repository Exception") }
        }

        OrderDas orderDas = new OrderDas(stubbedOrderMongoAdapter, stubbedOrderRepository)

        when:

        orderDas.saveOrder(order)

        then:

        thrown(Exception)
    }

    def "when getOrderStream then return all Order domain objects"() {

        given:

        def stubbedOrderMongoAdapter = Stub(OrderMongoAdapter) {
            domain2Document(order) >> orderDocument
            domain2Document(order2) >> orderDocument2
            document2Domain(orderDocument) >> order
            document2Domain(orderDocument2) >> order2
        }

        def stubbedOrderRepository = Stub(OrderRepository) {
            save(orderDocument) >> Mono.just(order)
            save(orderDocument2) >> Mono.just(order2)
            findOrderDocumentBy() >> Flux.just(orderDocument,orderDocument2)
        }

        OrderDas orderDas = new OrderDas(stubbedOrderMongoAdapter, stubbedOrderRepository)

        when:

        Flux<Order> stream = orderDas.getOrderStream()

        then:

        StepVerifier.create(stream)
                .expectNext(order)
                .expectNext(order2)
                .verifyComplete()

    }

}
