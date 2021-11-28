package com.achizhikov.kotlinutilitiesbot.telegram.messages

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

val MAIN_MARKUP: ReplyKeyboardMarkup = ReplyKeyboardMarkup.builder()
    .keyboardRow(KeyboardRow(listOf(KeyboardButton("Получить данные"))))
    .keyboardRow(KeyboardRow(listOf(KeyboardButton("Ввести данные"))))
    .oneTimeKeyboard(true)
    .build()

internal val GET_DATA_MARKUP = ReplyKeyboardMarkup.builder()
    .keyboardRow(KeyboardRow(listOf(KeyboardButton("Короткий отчёт"))))
    .keyboardRow(KeyboardRow(listOf(KeyboardButton("Excel-таблица со всеми данными"))))
    .oneTimeKeyboard(true)
    .build()

internal val INPUT_DATA_MARKUP = ReplyKeyboardMarkup.builder()
    .keyboardRow(KeyboardRow(listOf(KeyboardButton("Показания счётчиков"))))
    .keyboardRow(KeyboardRow(listOf(KeyboardButton("Тариф"))))
    .oneTimeKeyboard(true)
    .build()

internal val INPUT_DATE_MARKUP = ReplyKeyboardMarkup.builder()
    .keyboardRow(KeyboardRow(listOf(KeyboardButton("Сегодня"))))
    .oneTimeKeyboard(true)
    .build()

internal val DEFAULT_MARKUP = ReplyKeyboardMarkup.builder()
    .clearKeyboard()
    .oneTimeKeyboard(true)
    .build()
