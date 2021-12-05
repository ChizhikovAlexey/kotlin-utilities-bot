package com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers.inputud

import com.achizhikov.kotlinutilitiesbot.telegram.State
import com.achizhikov.kotlinutilitiesbot.telegram.messages.DEFAULT_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts.ChatContext
import com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers.Handler
import com.achizhikov.kotlinutilitiesbot.telegram.messages.hasNonEmptyMessage
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Component
class InputUdDateHandler : Handler {
    override val state = State.INPUT_UD_DATE

    override fun processUpdate(update: Update, context: ChatContext): List<BotApiMethod<Message>> {
        if (!update.hasNonEmptyMessage()) return listOf()
        val sendMessage = SendMessage.builder().chatId(update.message.chatId.toString())
        return when (val text = update.message.text) {
            "Сегодня" -> {
                context.state = State.INPUT_UD_ELECTRICITY
                context.utilitiesDataBuilder.date = LocalDate.now()
                listOf(sendMessage.replyMarkup(DEFAULT_MARKUP).text("Электроэнергия").build())
            }
            else -> {
                try {
                    context.state = State.INPUT_UD_ELECTRICITY
                    context.utilitiesDataBuilder.date = LocalDate.parse(text)
                    listOf(sendMessage.replyMarkup(DEFAULT_MARKUP).text("Электроэнергия").build())
                } catch (exc: DateTimeParseException) {
                    listOf(sendMessage.text("Неправильный формат даты").build())
                }
            }
        }
    }
}