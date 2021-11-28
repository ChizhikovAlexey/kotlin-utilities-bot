package com.achizhikov.kotlinutilitiesbot.telegram.messages.handlers

import com.achizhikov.kotlinutilitiesbot.database.DataService
import com.achizhikov.kotlinutilitiesbot.reports.excel.createExcel
import com.achizhikov.kotlinutilitiesbot.telegram.State
import com.achizhikov.kotlinutilitiesbot.telegram.messages.MAIN_MARKUP
import com.achizhikov.kotlinutilitiesbot.telegram.messages.contexts.ChatContext
import com.achizhikov.kotlinutilitiesbot.telegram.messages.hasNonEmptyMessage
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class GetDataHandler(private val dataService: DataService) : Handler {
    override val state = State.GET_DATA

    override fun processUpdate(update: Update, context: ChatContext): List<PartialBotApiMethod<Message>> {
        if (!update.hasNonEmptyMessage()) return listOf()
        val sendMessage = SendMessage.builder().chatId(update.message.chatId.toString())
        return when (update.message.text) {
            "Короткий отчёт" -> {
                context.state = State.MAIN
                val shortReport = dataService.getActualShortReport()
                listOf(
                    sendMessage.replyMarkup(MAIN_MARKUP).text(shortReport)
                        .build()
                )
            }
            "Excel-таблица со всеми данными" -> {
                context.state = State.MAIN
                val allReports = dataService.getAllReports()
                return if (allReports.isEmpty()) {
                    listOf(sendMessage.text("Недостаточно данных!").replyMarkup(MAIN_MARKUP).build())
                } else {
                    val excel = createExcel(allReports)
                    if (excel == null) {
                        listOf(sendMessage.text("Недостаточно данных!").replyMarkup(MAIN_MARKUP).build())
                    } else {
                        listOf(
                            sendMessage.text("Готово :)").build(),
                            SendDocument.builder()
                                .chatId(context.chatId.toString())
                                .replyMarkup(MAIN_MARKUP)
                                .document(InputFile(excel))
                                .build()
                        )
                    }

                }

            }
            "Назад" -> {
                context.state = State.MAIN
                listOf(sendMessage.text("Ладно :)").replyMarkup(MAIN_MARKUP).build())
            }
            else -> listOf(
                sendMessage
                    .text("Используй кнопки :)")
                    .build()
            )
        }
    }
}