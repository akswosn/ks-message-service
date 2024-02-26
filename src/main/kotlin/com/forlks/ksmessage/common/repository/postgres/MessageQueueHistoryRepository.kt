package com.forlks.ksmessage.common.repository.postgres

import com.forlks.ksmessage.common.entity.postgres.MessageQueueHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MessageQueueHistoryRepository: JpaRepository<MessageQueueHistoryEntity, Long> {
}
