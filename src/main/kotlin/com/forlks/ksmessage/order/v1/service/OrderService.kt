package com.forlks.ksmessage.order.v1.service

import com.forlks.ksmessage.order.v1.controller.OrderController
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class OrderService(
) {
    private val log = KotlinLogging.logger{}

    fun orderCoffee(dto: OrderController.OrderCoffeeDto){
    }

    fun orderCoffees(dto: OrderController.OrderCoffeeDto){
    }
}
