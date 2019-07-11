package com.datahack.eventdeliveroo.order.infrastructure

import com.datahack.eventdeliveroo.order.domain.model.Order
import com.datahack.eventdeliveroo.order.domain.model.OrderState
import com.datahack.eventdeliveroo.order.domain.service.IOrderBuilder
import com.datahack.eventdeliveroo.order.infrastructure.kafka.IOrderSender
import com.datahack.eventdeliveroo.order.infrastructure.mongo.OrderDas
import com.datahack.eventdeliveroo.order.infrastructure.service.OrderService
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

class OrderServiceTest extends Specification {

    def stubbedOrderBuilder = Stub(IOrderBuilder)
    def stubbedOrderDas = Stub(OrderDas)
    def mockedOrderSender = Mock(IOrderSender)
    Order order = Order.builder()
            .id(UUID.randomUUID().toString())
            .orderState(OrderState.ORDERED)
            .build()

    OrderService orderService = new OrderService(stubbedOrderBuilder, mockedOrderSender, stubbedOrderDas)

    def "given correct Order built and das ok response when place order then return the persisted order"() {

        given:
        stubbedOrderBuilder.createOrder() >> order
        stubbedOrderDas.saveOrder(order) >> Mono.just(order)

        when:
        Mono<Order> result = orderService.placeOrder()

        then:
        1 * mockedOrderSender.send(_)
        StepVerifier.create(result)
                .expectNext(order)
                .verifyComplete()
    }

    def "given Order built and das ko response when place order then exception and no message send"() {

        given:
        stubbedOrderBuilder.createOrder() >> order
        stubbedOrderDas.saveOrder(order) >> { throw new Exception("Duplicated Order") }

        when:
        orderService.placeOrder()

        then:
        thrown(Exception)
        0 * mockedOrderSender.send(_)
    }

    def "given null orderBuilderResult when placeOrder then exception"() {

        given:
        stubbedOrderBuilder.createOrder() >> null

        when:
        orderService.placeOrder()

        then:
        thrown(Exception)
    }

    def "RetrieveOrder"() {
    }

}
