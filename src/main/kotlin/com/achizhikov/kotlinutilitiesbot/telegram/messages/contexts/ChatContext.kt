package com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts

import com.achizhikov.kotlinutilitiesbot.database.TariffBuilder
import com.achizhikov.kotlinutilitiesbot.database.UtilitiesDataBuilder
import com.achizhikov.kotlinutilitiesbot.telegram.State

data class ChatContext(
    val chatId: Long,
    var state: State = State.NOT_STARTED,
    var tariffBuilder: TariffBuilder = TariffBuilder(),
    var utilitiesDataBuilder: UtilitiesDataBuilder = UtilitiesDataBuilder()
)
