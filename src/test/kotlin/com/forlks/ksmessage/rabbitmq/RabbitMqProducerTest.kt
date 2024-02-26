package com.forlks.ksmessage.rabbitmq

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.Exception


@ExtendWith(SpringExtension::class)
@SpringBootTest
class RabbitMqProducerTest {

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate
    private val log = KotlinLogging.logger{}

    @Test
    fun sendMessage() {
        try {
            val exchangeName = "testExchange"
            val key = "testQueue"

            rabbitTemplate.convertAndSend(exchangeName, key, "Hello Ks Message Service")
            log.info { "Success" }
        } catch (e: Exception) {
            log.warn { "Fail!!!! reason......" }
            log.error { e.stackTraceToString() }

        }

    }
}
