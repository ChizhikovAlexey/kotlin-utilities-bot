package com.achizhikov.kotlinutilitiesbot.database

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class UtilitiesData(
    val date: LocalDate,
    val electricity: Int,
    val bathHotWater: Int,
    val bathColdWater: Int,
    val kitchenHotWater: Int,
    val kitchenColdWater: Int,
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

data class UtilitiesDataBuilder(
    var date: LocalDate? = null,
    var electricity: Int? = null,
    var bathHotWater: Int? = null,
    var bathColdWater: Int? = null,
    var kitchenHotWater: Int? = null,
    var kitchenColdWater: Int? = null
) {
    fun build(): UtilitiesData {
        if (date == null || electricity == null || bathHotWater == null || bathColdWater == null ||
            kitchenColdWater == null || kitchenHotWater == null
        ) {
            throw IllegalStateException("Fields must not be null! $this")
        }
        return UtilitiesData(
            date!!, electricity!!, bathHotWater!!, bathColdWater!!, kitchenHotWater!!, kitchenColdWater!!
        )
    }
}