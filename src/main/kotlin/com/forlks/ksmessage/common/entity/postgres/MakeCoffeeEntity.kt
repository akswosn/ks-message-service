package com.forlks.ksmessage.common.entity.postgres

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "make_coffee")
class MakeCoffeeEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long? = null,
        @Column(name = "name")
        var name: String? = null,
        @Column(name = "maker")
        var maker: String? = null,
        @Column(name = "quantity")
        var quantity: Int? = null,
        @Column(name = "created_at")
        var createdAt: ZonedDateTime? = null
){

}
