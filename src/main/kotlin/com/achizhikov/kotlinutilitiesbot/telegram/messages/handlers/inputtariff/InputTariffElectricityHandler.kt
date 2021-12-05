package com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers.inputtariff

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
class InputTariffElectricityHandler : Handler {
    override val state = State.INPUT_T_ELECTRICITY

    override fun processUpdate(update: Update, context: ChatContext): List<BotApiMethod<Message>> {
        if (!update.hasNonEmptyMessage()) return listOf()
        val sendMessage = SendMessage.builder().chatId(update.message.chatId.toString())
        return try {
            val electricity = update.message.text.toDouble()
            context.tariffBuilder.electricity = electricity
            context.state = State.INPUT_T_HW
            listOf(sendMessage.replyMarkup(DEFAULT_MARKUP).text("Горячая вода").build())
        } catch (exc: NumberFormatException) {
            listOf(sendMessage.replyMarkup(DEFAULT_MARKUP).text("Неправильный формат числа :(").build())
        }
    }
}