package com.forlks.ksmessage.config

import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

//@Component
abstract class RabbitMqRobinReceiver {
    private val log = KotlinLogging.logger{}

    abstract fun receiveMessage(message: String)
}
