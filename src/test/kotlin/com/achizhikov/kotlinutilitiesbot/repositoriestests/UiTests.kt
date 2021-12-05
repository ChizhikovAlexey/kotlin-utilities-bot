package com.achizhikov.kotlinutilitiesbot.repositoriestests

import com.achizhikov.kotlinutilitiesbot.reports.ShortReport
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UiTests {
    @Test
    fun uiShortReportTest() {
        val uiShortReportString = ShortReport(secondDate, newerData, olderData, newerTariff).toUiString()
        Assertions.assertEquals(
            """
            Короткий отчёт от 2020-02-02:
            электроэнергия: 100 x 1.1 = 110.0 ₽
            горячая вода: 20 x 2.2 = 44.0 ₽
            холодная вода: 0 x 3.3 = 0.0 ₽
            водоотведение: 20 x 0.0 = 0.0 ₽
            
            Итого: 154.0 ₽
            """.trimIndent().trimStart(),

            uiShortReportString
        )
    }
}