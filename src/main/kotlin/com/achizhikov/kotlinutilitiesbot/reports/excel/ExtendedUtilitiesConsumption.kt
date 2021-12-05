package com.achizhikov.kotlinutilitiesbot.reports.excel

import com.achizhikov.kotlinutilitiesbot.database.UtilitiesData

data class ExtendedUtilitiesConsumption(
    val electricity: Int,
    val bathHotWater: Int,
    val bathColdWater: Int,
    val kitchenHotWater: Int,
    val kitchenColdWater: Int,
    val drainage: Int
) {
    constructor(newerData: UtilitiesData, olderData: UtilitiesData) : this(
        newerData.electricity - olderData.electricity,
        newerData.bathHotWater - olderData.bathHotWater,
        newerData.bathColdWater - olderData.bathColdWater,
        newerData.kitchenHotWater - olderData.kitchenHotWater,
        newerData.kitchenColdWater - olderData.kitchenColdWater,
        newerData.bathHotWater - olderData.bathHotWater +
                newerData.bathColdWater - olderData.bathColdWater +
                newerData.kitchenHotWater - olderData.kitchenHotWater +
                newerData.kitchenColdWater - olderData.kitchenColdWater
    ) {
        val errorString = "Error while creating a UtilitiesConsumption instance " +
                "using UtilitiesData's: newerData = $newerData, olderData = $olderData!"
        if (electricity < 0) {
            throw IllegalArgumentException("$errorString electricity = $electricity < 0!")
        }
        if (bathHotWater < 0) {
            throw IllegalArgumentException("$errorString athHotWater = $bathHotWater < 0!")
        }
        if (bathColdWater < 0) {
            throw IllegalArgumentException("$errorString bathColdWater = $bathColdWater < 0!")
        }
        if (kitchenHotWater < 0) {
            throw IllegalArgumentException("$errorString kitchenHotWater = $kitchenHotWater < 0!")
        }
        if (kitchenColdWater < 0) {
            throw IllegalArgumentException("$errorString kitchenColdWater = $kitchenColdWater < 0!")
        }
        if (drainage < 0) {
            throw IllegalArgumentException("$errorString drainage = $drainage < 0!")
        }
    }
}