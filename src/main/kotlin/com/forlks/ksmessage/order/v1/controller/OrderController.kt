package com.forlks.ksmessage.order.v1.controller

import com.crypted.kscommon.response.KsResponse
import com.crypted.kscommon.response.KsResponseEntity
import com.forlks.ksmessage.order.v1.dto.OrderCoffeeDto
import com.forlks.ksmessage.order.v1.service.OrderService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController



@RestController
@RequestMapping("/api/v1/order")
class OrderController(
        private val orderService: OrderService
) {
    private val log = KotlinLogging.logger{}

    @PostMapping("/coffee")
    fun coffee(@RequestBody dto: OrderCoffeeDto): ResponseEntity<KsResponseEntity> = try {
        orderService.orderCoffee(dto)
        KsResponse.KS_SUCCESS.toDataResponse(mapOf("result" to "success"))
    }
    catch (e: Exception){
        log.error { "/coffee Error ::: $e" }
        KsResponse.KS_SERVER_INTERNAL_ERROR.toMsgResponse<String>(e.message)
    }


    @PostMapping("/coffees")
    fun coffees(@RequestBody dto: OrderCoffeeDto): ResponseEntity<KsResponseEntity> = try {
        orderService.orderCoffees(dto)
        KsResponse.KS_SUCCESS.toDataResponse(mapOf("result" to "success"))
    }
    catch (e: Exception){
        log.error { "/coffees Error ::: $e" }
        KsResponse.KS_SERVER_INTERNAL_ERROR.toMsgResponse<String>(e.message)
    }

}
