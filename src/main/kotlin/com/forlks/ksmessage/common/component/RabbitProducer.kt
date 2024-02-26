package com.forlks.ksmessage.common.component

import com.forlks.ksmessage.common.dto.rabbitmq.CoffeeDto

interface RabbitProducer {
    val roundKeyPrefix: String
        get() = "order."

    fun send(id: Long, dto: CoffeeDto)
}
