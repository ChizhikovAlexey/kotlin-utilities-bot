package com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers

import com.achizhikov.kotlinutilitiesbot.telegram.State
import com.achizhikov.kotlinutilitiesbot.telegram.messages.INPUT_DATE_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.MAIN_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts.ChatContext
import com.achizhikov.kotlinutilitiesbot.telegram.messages.hasNonEmptyMessage
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class InputDataHandler : Handler {
    override val state = State.INPUT_DATA

    override fun processUpdate(update: Update, context: ChatContext): List<BotApiMethod<Message>> {
        if (!update.hasNonEmptyMessage()) return listOf()
        val sendMessage = SendMessage.builder().chatId(update.message.chatId.toString())
        return when (update.message.text) {
            "Показания счётчиков" -> {
                context.state = State.INPUT_UD_DATE
                listOf(sendMessage.replyMarkup(INPUT_DATE_MARKUP).text("Дата показаний").build())
            }
            "Тариф" -> {
                context.state = State.INPUT_T_DATE
                listOf(sendMessage.replyMarkup(INPUT_DATE_MARKUP).text("Дата тарифа").build())
            }
            "Назад" -> {
                context.state = State.MAIN
                listOf(sendMessage.text("Ладно :)").replyMarkup(MAIN_MARKUP).build())
            }
            else -> listOf(sendMessage.text("Используй кнопки :)").build())
        }
    }
}