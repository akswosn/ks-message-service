package com.forlks.ksmessage.order.v1.service

import com.forlks.ksmessage.common.component.RabbitRoundRobinProducer
import com.forlks.ksmessage.common.component.RabbitWorkersProducer
import com.forlks.ksmessage.common.dto.rabbitmq.CoffeeDto
import com.forlks.ksmessage.common.dto.rabbitmq.asMap
import com.forlks.ksmessage.common.entity.postgres.MessageQueueHistoryEntity
import com.forlks.ksmessage.common.repository.postgres.MessageQueueHistoryRepository
import com.forlks.ksmessage.order.v1.dto.OrderCoffeeDto
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import kotlin.Exception
import kotlin.jvm.Throws

@Service
class OrderService(
        private val rabbitRoundRobinProducer: RabbitRoundRobinProducer,
        private val workersProducer: RabbitWorkersProducer,
        private val messageQueueHistoryRepository: MessageQueueHistoryRepository
){
    private val log = KotlinLogging.logger{}

    fun orderCoffee(dto: OrderCoffeeDto){
        var messageId = 0L

        try{
            val requestDto = CoffeeDto(
                    name=dto.name, quantity = dto.quantity
            )
            messageId = messageSave(workersProducer.exchange, requestDto)
            workersProducer.send(messageId, requestDto)
        }
        catch (e : Exception){
            log.error { "Error [message=$messageId] ::: $e" }
            if(messageId > 0L){ // delete 처리 (메시지 발송 오류)
                messageDelete(messageId)
            }
        }
    }

    fun orderCoffees(dto: OrderCoffeeDto){
        var messageId = 0L
        try {
            val requestDto = CoffeeDto(
                    name=dto.name, quantity = dto.quantity
            )
            messageId= messageSave(rabbitRoundRobinProducer.exchange, requestDto)
            rabbitRoundRobinProducer.send(messageId, requestDto)
        }
        catch (e: Exception){
            log.error { "Error [message=$messageId] ::: $e" }
            if(messageId > 0L){ // delete 처리 (메시지 발송 오류)
                messageDelete(messageId)
            }
        }

    }

    @Throws(Exception::class)
    @Transactional(readOnly = false)
    fun messageSave(exchange: String, dto: CoffeeDto): Long{
        var messageEntity = MessageQueueHistoryEntity()
        messageEntity.exchange = exchange
        messageEntity.payload = dto.asMap()
        messageEntity.createdAt = ZonedDateTime.now()
        messageEntity.isProcess = false
        messageEntity.routingKey = null

        messageEntity = messageQueueHistoryRepository.save(messageEntity)
        return messageEntity.id
    }

    @Throws(Exception::class)
    @Transactional(readOnly = false)
    fun messageDelete(id: Long){
        val option = messageQueueHistoryRepository.findById(id)
        option.ifPresent{ message -> messageQueueHistoryRepository.delete(message) }
    }

}
