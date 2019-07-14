package com.datahack.eventdeliveroo.kitchen.infrastructure.service

import com.datahack.eventdeliveroo.kitchen.domain.model.Order
import com.datahack.eventdeliveroo.kitchen.domain.model.OrderState
import com.datahack.eventdeliveroo.kitchen.domain.service.IOrder
import com.datahack.eventdeliveroo.kitchen.infrastructure.kafka.IKitchenProducer
import spock.lang.Specification

class EventManagerTest extends Specification {

    IOrder stubbedOrder = Stub(IOrder)
    IKitchenProducer mockedKitchenProducer = Mock(IKitchenProducer)

    def order = Order.builder()
            .orderId(UUID.randomUUID().toString())
            .orderState(OrderState.ORDERED)
            .build()
    def updatedOrder = Order.builder()
            .orderId(order.orderId)
            .orderState(OrderState.READY)
            .build()

    def "given order when ManageEvent updated order send to kafka"() {

        given:

        stubbedOrder.updateOrderStatus(order) >> updatedOrder
        EventManager eventMockedManager = new EventManager(stubbedOrder, mockedKitchenProducer)

        when:

        eventMockedManager.manageEvent(order)

        then:

        1 * mockedKitchenProducer.send(updatedOrder)
    }

}
