package com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers.inputud

import com.achizhikov.kotlinutilitiesbot.database.DataService
import com.achizhikov.kotlinutilitiesbot.telegram.State
import com.achizhikov.kotlinutilitiesbot.telegram.messages.DEFAULT_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.MAIN_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts.ChatContext
import com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers.Handler
import com.achizhikov.kotlinutilitiesbot.telegram.messages.hasNonEmptyMessage
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class InputUdKitchenColdWaterHandler(val dataService: DataService) : Handler {
    override val state = State.INPUT_UD_KCW

    override fun processUpdate(update: Update, context: ChatContext): List<BotApiMethod<Message>> {
        if (!update.hasNonEmptyMessage()) return listOf()
        val sendMessage = SendMessage.builder().chatId(update.message.chatId.toString())
        return try {
            val kitchenColdWater = Integer.parseInt(update.message.text)
            context.utilitiesDataBuilder.kitchenColdWater = kitchenColdWater
            context.state = State.MAIN
            val uploaded = dataService.addUtilitiesData(context.utilitiesDataBuilder.build())
            if (uploaded != null) {
                listOf(
                    sendMessage
                        .replyMarkup(MAIN_MARKUP)
                        .text("Готово!\nЗагрузил ${uploaded.toUiString()}")
                        .build()
                )
            } else {
                listOf(
                    sendMessage
                        .replyMarkup(DEFAULT_MARKUP)
                        .text("Неизвестная ошибка!\nПытался загрузить ${context.utilitiesDataBuilder}")
                        .build()
                )
            }
        } catch (exc: NumberFormatException) {
            listOf(sendMessage.replyMarkup(DEFAULT_MARKUP).text("Неправильный формат числа :(").build())
        }
    }
}