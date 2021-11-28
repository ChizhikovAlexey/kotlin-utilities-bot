package com.achizhikov.kotlinutilitiesbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class KotlinUtilitiesBotApplication

fun main(args: Array<String>) {
    runApplication<KotlinUtilitiesBotApplication>(*args)
}