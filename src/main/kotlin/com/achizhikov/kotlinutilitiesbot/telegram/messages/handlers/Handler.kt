package com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers

import com.achizhikov.kotlinutilitiesbot.telegram.State
import com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts.ChatContext
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

interface Handler {
    val state: State
    fun processUpdate(update: Update, context: ChatContext): List<PartialBotApiMethod<Message>>
}



