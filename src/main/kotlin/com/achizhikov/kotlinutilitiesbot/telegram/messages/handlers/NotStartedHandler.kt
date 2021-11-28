package com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers

import com.achizhikov.kotlinutilitiesbot.telegram.State
import com.achizhikov.kotlinutilitiesbot.telegram.messages.MAIN_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts.ChatContext
import com.achizhikov.kotlinutilitiesbot.telegram.messages.hasEmptyMessage
import com.achizhikov.kotlinutilitiesbot.telegram.messages.hasNonEmptyMessage
import com.achizhikov.kotlinutilitiesbot.telegram.messages.replyToEmptyMessage
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class NotStartedHandler : Handler {
    override val state = State.NOT_STARTED

    override fun processUpdate(update: Update, context: ChatContext): List<BotApiMethod<Message>> {
        context.state = State.MAIN
        return when {
            update.hasEmptyMessage() -> listOf(replyToEmptyMessage(update))

            update.hasNonEmptyMessage() -> listOf(
                SendMessage.builder()
                    .replyMarkup(MAIN_MARKUP)
                    .chatId(update.message.chatId.toString())
                    .text("Привет, ${update.message.chat.firstName}!")
                    .build()
            )

            else -> listOf()
        }
    }
}