package com.jayway.ihc.ihcweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IhcWebApplication

fun main(args: Array<String>) {
    runApplication<IhcWebApplication>(*args)
}
