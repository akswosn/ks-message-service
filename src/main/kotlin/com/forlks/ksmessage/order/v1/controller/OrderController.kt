package com.forlks.ksmessage.order.v1.controller

import com.forlks.ksmessage.order.v1.service.OrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/order")
class OrderController(
        private val orderService: OrderService
) {

    @PostMapping("/coffee")
    fun coffee(@RequestBody dto: OrderCoffeeDto){

    }


    @PostMapping("/coffees")
    fun coffees(@RequestBody dto: OrderCoffeeDto){}


    class OrderCoffeeDto(
            val name: String? = null,
            val quantity: Int? = null
    ){}
}
