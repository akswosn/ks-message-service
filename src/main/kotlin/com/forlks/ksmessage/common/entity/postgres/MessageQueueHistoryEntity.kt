package com.forlks.ksmessage.common.entity.postgres

import com.vladmihalcea.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.io.Serializable
import java.time.ZonedDateTime

@Entity
@Table(name = "message_queue_history")
class MessageQueueHistoryEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long = 0L,
        @Column(name = "exchange")
        var exchange: String? = null,
        @Column(name = "routing_key")
        var routingKey: String? = null,
        @Type(JsonType::class)
        @Column(name = "payload", columnDefinition="json")
        var payload: Map<String, Any?>? = null,
        @Column(name = "is_process")
        var isProcess: Boolean? = null,
        @Column(name = "created_at")
        var createdAt: ZonedDateTime? = null,
): Serializable{
}
