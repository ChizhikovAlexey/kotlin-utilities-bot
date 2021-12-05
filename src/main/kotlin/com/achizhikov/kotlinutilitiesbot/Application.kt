package com.achizhikov.kotlinutilitiesbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class KotlinUtilitiesBotApplication

fun main(args: Array<String>) {
    runApplication<KotlinUtilitiesBotApplication>(*args)
}