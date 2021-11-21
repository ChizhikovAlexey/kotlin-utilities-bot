package com.achizhikov.kotlinutilitiesbot

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class UtilitiesData(
    @Id val id: Int?,
    val date: LocalDate,
    val electricity: Int,
    val bathHotWater: Int,
    val bathColdWater: Int,
    val kitchenHotWater: Int,
    val kitchenColdWater: Int
)

data class Tariff(
    @Id val id: Int?,
    val date: LocalDate,
    val electricity: Double,
    val hotWater: Double,
    val coldWater: Double,
    val drainage: Double
)