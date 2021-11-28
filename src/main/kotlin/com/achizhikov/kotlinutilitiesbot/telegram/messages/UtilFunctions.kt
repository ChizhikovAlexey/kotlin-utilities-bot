package com.achizhikov.kotlinutilitiesbot.telegram.messages

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

internal fun Update.hasEmptyMessage() = this.hasMessage() && (!this.message.hasText() || this.message.text.isBlank())
internal fun Update.hasNonEmptyMessage() = this.hasMessage() && this.message.hasText() && this.message.text.isNotBlank()
internal fun replyToEmptyMessage(update: Update) = SendMessage.builder()
    .chatId(update.message.chatId.toString())
    .text("Отправлено пустое сообщение!")
    .build()
