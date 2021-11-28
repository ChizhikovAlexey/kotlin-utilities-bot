package com.achizhikov.kotlinutilitiesbot.database

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class UtilitiesData(
    var date: LocalDate? = null,
    var electricity: Int? = null,
    var bathHotWater: Int? = null,
    var bathColdWater: Int? = null,
    var kitchenHotWater: Int? = null,
    var kitchenColdWater: Int? = null,
    @Id val id: Int? = null
) {
    fun toUiString() = """
        показания от $date
        электроэнергия: $electricity квт.ч.
        ванная
            горячая вода: $bathHotWater м^3
            холодная вода: $bathColdWater м^3
        кухня
            горячая вода: $kitchenHotWater м^3
            холодная вода: $kitchenColdWater м^3
    """.trimIndent()
}

data class Tariff(
    var date: LocalDate? = null,
    var electricity: Double? = null,
    var hotWater: Double? = null,
    var coldWater: Double? = null,
    var drainage: Double? = null,
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