package com.achizhikov.kotlinutilitiesbot.telegram

import com.achizhikov.kotlinutilitiesbot.telegram.messages.MAIN_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts.ChatContext
import com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers.Handler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.function.Function
import java.util.stream.Collectors

@Component
class TelegramBot(handlers: List<Handler>) : TelegramLongPollingBot() {
    private val statesByChatId = HashMap<Long, ChatContext>()
    private val handlersByState = handlers.stream().collect(Collectors.toMap(Handler::state, Function.identity()))

    override fun getBotToken(): String = System.getenv("KOTLIN_UTILITIES_BOT_TOKEN")

    override fun getBotUsername() = "Kotlin Utilities Bot"

    override fun onUpdateReceived(update: Update) {
        if (!update.hasMessage()) return
        val chatId = update.message.chatId
        val context = getContext(chatId)
        val state = context.state
        if (update.message.text == "/cancel") {
            cancel(context, chatId)
        } else {
            val handler =
                handlersByState[state] ?: throw IllegalStateException("Handler not found! $context; $handlersByState")
            handler.processUpdate(update, context).forEach { executable ->
                when (executable) {
                    is SendMessage -> execute(executable)
                    is SendDocument -> execute(executable)
                }
            }
        }
    }

    private fun getContext(chatId: Long): ChatContext {
        val state = statesByChatId[chatId]
        return if (state != null) {
            state
        } else {
            val context = ChatContext(chatId, State.NOT_STARTED)
            statesByChatId[chatId] = context
            context
        }
    }

    private fun cancel(context: ChatContext, chatId: Long) {
        context.state = State.MAIN
        execute(SendMessage.builder().chatId(chatId.toString()).text("Ладно :)").replyMarkup(MAIN_MARKUP).build())
    }
}