package com.forlks.ksmessage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KsMessageServiceApplication

fun main(args: Array<String>) {
    runApplication<KsMessageServiceApplication>(*args)
}
