package com.forlks.ksmessage.maker.v1.consumer

import com.forlks.ksmessage.config.RabbitMqRobinReceiver
import mu.KotlinLogging
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class AssistantChiefConsumer : RabbitMqRobinReceiver() {
    private val actor: String = "AssistantChief"
    private val log = KotlinLogging.logger{}
    @RabbitListener(queues=["forlks.make.coffee.queue"])
    fun workConsumer(message: Message){
        log.info { "#### [actor=$actor] workConsumer [queue=forlks.make.coffee.queue] ::: $message" }
    }

    override fun receiveMessage(message: String) {
        log.info { "#### [actor=$actor] workConsumer [queue=forlks.make.coffee.queue] ::: $message" }
    }

//    @RabbitListener(queues=["forlks.make.coffees.queue"])
//    fun robinConsumer(message: Message){
//        log.info { "#### [actor=$actor] workConsumer [queue=forlks.make.coffee.queue] ::: $message" }
//    }
}
