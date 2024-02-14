package com.forlks.message.ksmessageservice.config

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
class RabbitmqProducer(
    @Value("\${spring.rabbitmq.host}")
    val host: String,
    @Value("\${spring.rabbitmq.port}")
    val port: Int,
    @Value("\${spring.rabbitmq.username}")
    val username: String,
    @Value("\${spring.rabbitmq.password}")
    val password: String
) {
    private lateinit var _rabbitMqTemplate: RabbitTemplate
    private val log = KotlinLogging.logger{}

    @PostConstruct
    fun init(){
        //초기화
        _rabbitMqTemplate = RabbitTemplate(connectionFactory())
        _rabbitMqTemplate.messageConverter = messageConverter()

        log.info { "#### info rabbitMq producer :::: $_rabbitMqTemplate" }
    }


    private fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory(host)
        connectionFactory.port = port
        connectionFactory.username = username
        connectionFactory.setPassword(password)
        connectionFactory.setRequestedHeartBeat(30)
        connectionFactory.setRecoveryListener(RabbitRecoveryListener())

        return connectionFactory
    }


    private fun messageConverter(): MessageConverter = Jackson2JsonMessageConverter()



    //Getter
    fun getRabbitTemplate(): RabbitTemplate = _rabbitMqTemplate


    private class RabbitRecoveryListener: RecoveryListener{
        private val log = KotlinLogging.logger{}
        override fun handleRecovery(p0: Recoverable?) {
           log.info { "Call handleRecovery ::: $p0" }
        }

        override fun handleRecoveryStarted(p0: Recoverable?) {
            log.info { "Call handleRecoveryStarted ::: $p0" }

        }
    }
}
