package com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts

import com.achizhikov.kotlinutilitiesbot.database.Tariff
import com.achizhikov.kotlinutilitiesbot.database.UtilitiesData
import com.achizhikov.kotlinutilitiesbot.telegram.State

data class ChatContext(
    val chatId: Long,
    var state: State = State.NOT_STARTED,
    var tariff: Tariff = Tariff(),
    var utilitiesData: UtilitiesData = UtilitiesData()
)
