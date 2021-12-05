package com.achizhikov.kotlinutilitiesbot.database

import org.springframework.data.annotation.Id
import java.time.LocalDate


data class Tariff(
    val date: LocalDate,
    val electricity: Double,
    val hotWater: Double,
    val coldWater: Double,
    val drainage: Double,
    @Id val id: Int? = null
) {
    fun toUiString() = """
        тариф от $date
        электроэнергия: $electricity ₽/квт.ч
        горячая вода: $hotWater ₽/м^3
        холодная вода: $coldWater ₽/м^3
        водоотведение: $drainage ₽/м^3
    """.trimIndent()
}

data class TariffBuilder(
    var date: LocalDate? = null,
    var electricity: Double? = null,
    var hotWater: Double? = null,
    var coldWater: Double? = null,
    var drainage: Double? = null
) {
    fun build(): Tariff {
        if (date == null || electricity == null || hotWater == null || coldWater == null || drainage == null) {
            throw IllegalStateException("Fields must not be null! $this")
        }
        return Tariff(date!!, electricity!!, hotWater!!, coldWater!!, drainage!!)
    }
}