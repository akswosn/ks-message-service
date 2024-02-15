package com.forlks.ksmessage.common.repository.postgres

import com.forlks.ksmessage.common.entity.postgres.MakeCoffeeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MakeCoffeeRepository: JpaRepository<MakeCoffeeEntity, Long> {
}
