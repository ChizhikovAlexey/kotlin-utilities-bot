package com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers

import com.achizhikov.kotlinutilitiesbot.telegram.State
import com.achizhikov.kotlinutilitiesbot.telegram.messages.GET_DATA_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.INPUT_DATA_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.MAIN_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts.ChatContext
import com.achizhikov.kotlinutilitiesbot.telegram.messages.hasNonEmptyMessage
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class MainHandler : Handler {
    override val state = State.MAIN

    override fun processUpdate(update: Update, context: ChatContext): List<BotApiMethod<Message>> {
        if (!update.hasNonEmptyMessage()) return listOf()
        val sendMessage = SendMessage.builder().chatId(update.message.chatId.toString())
        return when (update.message.text) {
            "Получить данные" -> {
                context.state = State.GET_DATA
                listOf(
                    sendMessage
                        .replyMarkup(GET_DATA_MARKUP)
                        .text("Какие?")
                        .build()
                )
            }
            "Ввести данные" -> {
                context.state = State.INPUT_DATA
                listOf(
                    sendMessage
                        .replyMarkup(INPUT_DATA_MARKUP)
                        .text("Какие?")
                        .build()
                )
            }
            else -> listOf(
                sendMessage
                    .replyMarkup(MAIN_MARKUP)
                    .text("Используй кнопки :)")
                    .build()
            )
        }
    }
}