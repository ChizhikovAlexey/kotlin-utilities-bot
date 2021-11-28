package com.achizhikov.kotlinutilitiesbot.reports

import com.achizhikov.kotlinutilitiesbot.database.Tariff
import com.achizhikov.kotlinutilitiesbot.database.UtilitiesData
import com.achizhikov.kotlinutilitiesbot.reports.text.UtilitiesConsumption
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
        val electricityCost = (consumption.electricity * tariff.electricity!!).roundToUi()
        val hotWaterCost = (consumption.hotWater * tariff.hotWater!!).roundToUi()
        val coldWaterCost = (consumption.coldWater * tariff.coldWater!!).roundToUi()
        val drainageCost = (consumption.drainage * tariff.drainage!!).roundToUi()
        val finalCost = electricityCost + hotWaterCost + coldWaterCost + drainageCost
        return """
        Короткий отчёт от $date:
        электроэнергия: ${consumption.electricity} x ${tariff.electricity} = $electricityCost₽
        горячая вода: ${consumption.hotWater} x ${tariff.hotWater} = $hotWaterCost₽
        холодная вода: ${consumption.coldWater} x ${tariff.coldWater} = $coldWaterCost₽
        электроэнергия: ${consumption.drainage} x ${tariff.drainage} = $drainageCost₽
        
        Итого: $finalCost₽
        """.trimIndent().trimStart()
    }

    private fun Double.roundToUi() = this.div(100).roundToInt().toDouble().times(100)

}

data class Report(
    val date: LocalDate,
    val newerData: UtilitiesData,
    val olderData: UtilitiesData,
    val tariff: Tariff
)
