package com.forlks.ksmessage.config

import com.rabbitmq.client.Recoverable
import com.rabbitmq.client.RecoveryListener
import mu.KotlinLogging
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMqConfig(
        @Value("\${spring.rabbitmq.host}")
        val host: String,
        @Value("\${spring.rabbitmq.port}")
        val port: Int,
        @Value("\${spring.rabbitmq.username}")
        val username: String,
        @Value("\${spring.rabbitmq.password}")
        val password: String,
        @Value("\${ks.rabbitmq.exchange.round-robin}")
        val robinExchange: String,
        @Value("\${ks.rabbitmq.queue.round-robin}")
        val robinQueue: String,
        @Value("\${ks.rabbitmq.exchange.works-queue}")
        val worksExchange: String,
        @Value("\${ks.rabbitmq.queue.works-queue}")
        val worksQueue: String
) {
    private val log = KotlinLogging.logger{}

    @Bean
    fun roundRobinQueue(): Queue {
        return Queue(robinQueue, false)
    }

    @Bean
    fun roundRobinExchange(): FanoutExchange {
        return FanoutExchange(robinExchange)
    }

    @Bean
    fun worksQueue(): Queue {
        return Queue(worksQueue, false)
    }

    @Bean
    fun worksExchange(): TopicExchange {
        return TopicExchange(worksExchange)
    }


    @Bean
    fun topicBinding(worksExchange: TopicExchange,worksQueue: Queue): Binding {
        val bind = BindingBuilder.bind(worksQueue)
                .to(worksExchange).with("order.*")
        log.info { "topicBinding ::: $bind" }
        return bind
    }

//    @Bean
//    fun fanOutBinding(roundRobinExchange: FanoutExchange,roundRobinQueue: Queue): Binding {
//        val bind =
//                BindingBuilder.bind(roundRobinQueue)
//                        .to(roundRobinExchange)
//        log.info { "fanOutBinding ::: $bind" }
//
//        return bind
//    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate
    {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = messageConverter()
        return rabbitTemplate
    }

    @Bean
    fun container(connectionFactory: ConnectionFactory?,
                  listenerAdapter: MessageListenerAdapter?): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory!!
        container.setQueueNames(robinQueue)
        container.setMessageListener(listenerAdapter!!)
        return container
    }

    @Bean
    fun listenerAdapter(receiver: RabbitMqRobinReceiver?): MessageListenerAdapter {
        return MessageListenerAdapter(receiver, "receiveMessage")
    }

    @Bean
    fun receiver(): RabbitMqRobinReceiver {
        return RabbitMqRobinReceiver()
    }

    @Bean
    fun connectionFactory(): ConnectionFactory
    {
        val connectionFactory = CachingConnectionFactory()
        connectionFactory.setHost(host)
        connectionFactory.port = port
        connectionFactory.username = username
        connectionFactory.setPassword(password)
        connectionFactory.setRequestedHeartBeat(30)
        connectionFactory.setRecoveryListener(RabbitRecoveryListener())
        return connectionFactory
    }

    @Bean
    fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    private class RabbitRecoveryListener: RecoveryListener {
        private val log = KotlinLogging.logger{}
        override fun handleRecovery(p0: Recoverable?) {
            log.info { "Call handleRecovery ::: $p0" }
        }

        override fun handleRecoveryStarted(p0: Recoverable?) {
            log.info { "Call handleRecoveryStarted ::: $p0" }
        }
    }
}
