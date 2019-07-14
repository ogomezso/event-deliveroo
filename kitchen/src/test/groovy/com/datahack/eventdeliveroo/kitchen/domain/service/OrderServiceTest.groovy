package com.datahack.eventdeliveroo.kitchen.domain.service

import com.datahack.eventdeliveroo.kitchen.domain.model.Order
import com.datahack.eventdeliveroo.kitchen.domain.model.OrderState
import spock.lang.Specification

class OrderServiceTest extends Specification {

    OrderService orderService = new OrderService()

    def order = Order.builder()
            .orderId(UUID.randomUUID().toString())
            .orderState(OrderState.ORDERED)
            .build()

    def "given a Order when UpdateOrderStatus return Order with status changed"() {

        when:

        Order result = orderService.updateOrderStatus(order)

        then:

        result.orderId == order.orderId
        result.orderState == OrderState.READY


    }
}
