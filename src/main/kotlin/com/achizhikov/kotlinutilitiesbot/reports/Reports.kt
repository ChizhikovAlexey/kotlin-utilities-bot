package com.achizhikov.kotlinutilitiesbot.reports

import com.achizhikov.kotlinutilitiesbot.database.Tariff
import com.achizhikov.kotlinutilitiesbot.database.UtilitiesData
import com.achizhikov.kotlinutilitiesbot.reports.text.UtilitiesConsumption
import java.text.DecimalFormat
import java.time.LocalDate
import kotlin.math.roundToInt

data class ShortReport(
    val date: LocalDate,
    val consumption: UtilitiesConsumption,
    val tariff: Tariff
) {
    constructor(date: LocalDate, newerData: UtilitiesData, olderData: UtilitiesData, tariff: Tariff) : this(
        date,
        UtilitiesConsumption(newerData, olderData),
        tariff
    )

    fun toUiString(): String {
        val electricityCost = (consumption.electricity * tariff.electricity!!).times(100).roundToInt().div(100.0)
        val hotWaterCost = (consumption.hotWater * tariff.hotWater!!).times(100).roundToInt().div(100.0)
        val coldWaterCost = (consumption.coldWater * tariff.coldWater!!).times(100).roundToInt().div(100.0)
        val drainageCost = (consumption.drainage * tariff.drainage!!).times(100).roundToInt().div(100.0)
        val finalCost = (electricityCost + hotWaterCost + coldWaterCost + drainageCost).times(100).roundToInt().div(100.0)
        return """
        Короткий отчёт от $date:
        электроэнергия: ${consumption.electricity} x ${tariff.electricity} = $electricityCost ₽
        горячая вода: ${consumption.hotWater} x ${tariff.hotWater} = $hotWaterCost ₽
        холодная вода: ${consumption.coldWater} x ${tariff.coldWater} = $coldWaterCost ₽
        водоотведение: ${consumption.drainage} x ${tariff.drainage} = $drainageCost ₽
        
        Итого: $finalCost₽
        """.trimIndent().trimStart()
    }
}

data class Report(
    val date: LocalDate,
    val newerData: UtilitiesData,
    val olderData: UtilitiesData,
    val tariff: Tariff
)
