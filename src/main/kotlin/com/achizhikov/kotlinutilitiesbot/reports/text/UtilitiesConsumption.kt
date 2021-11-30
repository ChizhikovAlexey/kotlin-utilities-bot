package com.achizhikov.kotlinutilitiesbot.reports.text

import com.achizhikov.kotlinutilitiesbot.database.UtilitiesData

data class UtilitiesConsumption(
    val electricity: Int,
    val hotWater: Int,
    val coldWater: Int,
    val drainage: Int
) {
    constructor(newerData: UtilitiesData, olderData: UtilitiesData) : this(
        newerData.electricity!! - olderData.electricity!!,
        newerData.bathHotWater!! + newerData.kitchenHotWater!! - olderData.bathHotWater!! - olderData.kitchenHotWater!!,
        newerData.bathColdWater!! + newerData.kitchenColdWater!! - olderData.bathColdWater!! - olderData.kitchenColdWater!!,
        newerData.bathHotWater!! + newerData.kitchenHotWater!! - olderData.bathHotWater!! - olderData.kitchenHotWater!! +
                newerData.bathColdWater!! + newerData.kitchenColdWater!! - olderData.bathColdWater!! - olderData.kitchenColdWater!!
    ) {
        val errorString = "Error while creating a UtilitiesConsumption instance " +
                "using UtilitiesData's: newerData = $newerData, olderData = $olderData!"
        if (electricity < 0) {
            throw IllegalArgumentException("$errorString Electricity = $electricity < 0!")
        }
        if (hotWater < 0) {
            throw IllegalArgumentException("$errorString Electricity = $hotWater < 0!")
        }
        if (coldWater < 0) {
            throw IllegalArgumentException("$errorString Electricity = $coldWater < 0!")
        }
        if (drainage < 0) {
            throw IllegalArgumentException("$errorString Electricity = $drainage < 0!")
        }
    }
}