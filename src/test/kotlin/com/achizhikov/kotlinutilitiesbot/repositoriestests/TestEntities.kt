package com.achizhikov.kotlinutilitiesbot.repositoriestests

import com.achizhikov.kotlinutilitiesbot.database.Tariff
import com.achizhikov.kotlinutilitiesbot.database.UtilitiesData
import java.time.LocalDate

// DATES
val firstDate: LocalDate = LocalDate.of(2020, 1, 1)
val secondDate: LocalDate = LocalDate.of(2020, 2, 2)

// UTILITIES DATA
val newerData = UtilitiesData(secondDate, 200, 20, 20, 20, 20)
val olderData = UtilitiesData(firstDate, 100, 10, 20, 10, 20)

// TARIFFS
val olderTariff = Tariff(firstDate, 1.1, 2.2, 3.3, 0.0)
val newerTariff = Tariff(firstDate, 1.1, 2.2, 3.3, 0.0)

fun Tariff.equalsIgnoringId(that: Any): Boolean {
    if (this === that) return true
    if (that !is Tariff) return false
    if (this.date != that.date) return false
    if (this.electricity != that.electricity) return false
    if (this.hotWater != that.hotWater) return false
    if (this.coldWater != that.coldWater) return false
    if (this.drainage != that.drainage) return false
    return true
}

fun UtilitiesData.equalsIgnoringId(that: Any): Boolean {
    if (this === that) return true
    if (that !is UtilitiesData) return false
    if (this.date != that.date) return false
    if (this.electricity != that.electricity) return false
    if (this.bathColdWater != that.bathColdWater) return false
    if (this.bathHotWater != that.bathHotWater) return false
    if (this.kitchenHotWater != that.kitchenHotWater) return false
    if (this.kitchenColdWater != that.kitchenColdWater) return false
    return true
}