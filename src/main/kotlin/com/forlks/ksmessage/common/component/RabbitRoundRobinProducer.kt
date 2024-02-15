package com.forlks.ksmessage.common.component

import com.forlks.ksmessage.common.dto.rabbitmq.CoffeeDto
import com.rabbitmq.client.Recoverable
import com.rabbitmq.client.RecoveryListener
import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Rabbit MQ Producer Component
 */
@Component
class RabbitRoundRobinProducer(
   private val rabbitTemplate: RabbitTemplate,
   @Value("\${ks.rabbitmq.exchange.round-robin}")
   val exchange: String,
) {

}
