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

@Component
class InputUdElectricityHandler : Handler {
    override val state = State.INPUT_UD_ELECTRICITY

    override fun processUpdate(update: Update, context: ChatContext): List<BotApiMethod<Message>> {
        if (!update.hasNonEmptyMessage()) return listOf()
        val sendMessage = SendMessage.builder().chatId(update.message.chatId.toString())
        return try {
            val electricity = Integer.parseInt(update.message.text)
            context.utilitiesDataBuilder.electricity = electricity
            context.state = State.INPUT_UD_BHW
            listOf(sendMessage.replyMarkup(DEFAULT_MARKUP).text("Ванная: горячая вода").build())
        } catch (exc: NumberFormatException) {
            listOf(sendMessage.replyMarkup(DEFAULT_MARKUP).text("Неправильный формат числа :(").build())
        }
    }
}