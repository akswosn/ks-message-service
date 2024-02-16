package com.forlks.ksmessage.common.dto.rabbitmq

import kotlin.reflect.full.memberProperties

class CoffeeDto(
        val name: String? = null,
        val quantity: Int? = 0,
) {
}

fun CoffeeDto.asMap(): Map<String, Any?> = CoffeeDto::class.memberProperties.associate { it.name to it.get(this)}
