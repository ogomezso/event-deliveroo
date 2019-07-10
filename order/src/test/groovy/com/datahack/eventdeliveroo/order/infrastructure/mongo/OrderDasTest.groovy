package com.datahack.eventdeliveroo.order.infrastructure.mongo

import com.datahack.eventdeliveroo.order.domain.model.Order
import com.datahack.eventdeliveroo.order.domain.model.OrderState
import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.LocalDateTime

class OrderDasTest extends Specification {

    def "given Order domain object when PersistOrder then return Object Domain persisted"() {

        given:

        def order = Order.builder()
                .id(UUID.randomUUID().toString())
                .courierId(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .orderState(OrderState.READY)
                .build()
        def orderDocument = OrderDocument.builder()
                .id(order.getId())
                .courierId(order.getCourierId())
                .date(order.getDate())
                .orderState(order.getOrderState())
                .build()

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

    def "given duplicated Order domain object when PersistOrder then return Object Domain persisted"() {

        given:

        def order = Order.builder()
                .id(UUID.randomUUID().toString())
                .courierId(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .orderState(OrderState.READY)
                .build()
        def orderDocument = OrderDocument.builder()
                .id(order.getId())
                .courierId(order.getCourierId())
                .date(order.getDate())
                .orderState(order.getOrderState())
                .build()

        def stubbedOrderMongoAdapter = Stub(OrderMongoAdapter) {
            domain2Document(order) >> orderDocument
            document2Domain(Mono.just(orderDocument)) >> order
        }

        def stubbedOrderRepository = Stub(OrderRepository) {
            save(_) >> { throw new Exception("Duplicated Order") }
        }

        OrderDas orderDas = new OrderDas(stubbedOrderMongoAdapter, stubbedOrderRepository)

        when:

        orderDas.saveOrder(order)

        then:

        thrown(Exception)


    }

    def "RetrieveOrder"() {
    }
}
