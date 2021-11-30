package com.achizhikov.kotlinutilitiesbot.database

import com.achizhikov.kotlinutilitiesbot.reports.Report
import com.achizhikov.kotlinutilitiesbot.reports.ShortReport
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DataService(
    private val utilitiesDataRepository: UtilitiesDataRepository,
    private val tariffsRepository: TariffsRepository
) {
    private val logger = LoggerFactory.getLogger("DataService")

    fun getActualShortReport(): String {
        return try {
            val twoLatestData = utilitiesDataRepository.findTop2ByOrderByDateDesc()
            val tariffOptional = tariffsRepository.findTopByOrderByDateDesc()
            if (twoLatestData.size < 2 || tariffOptional.isEmpty) return "Недостаточно данных :("
            val tariff = tariffOptional.orElseThrow()
            val newerData = twoLatestData[0]
            val olderData = twoLatestData[1]
            ShortReport(LocalDate.now(), newerData, olderData, tariff).toUiString()
        } catch (exc: Exception) {
            logger.error("Error while getting actual report!", exc)
            "Неизвестная ошибка!"
        }
    }

    fun addUtilitiesData(utilitiesData: UtilitiesData): UtilitiesData? {
        return try {
            utilitiesDataRepository.save(utilitiesData)
        } catch (exc: Exception) {
            logger.error("Error while adding utilities data!", exc)
            null
        }
    }

    fun addTariff(tariff: Tariff): Tariff? {
        return try {
            tariffsRepository.save(tariff)
        } catch (exc: Exception) {
            logger.error("Error while adding tariff!", exc)
            null
        }
    }

    fun getAllReports(): List<Report> {
        val tariffsByDate = tariffsRepository.findAllByOrderByDateAsc()
        val utilitiesData = utilitiesDataRepository.findAllByOrderByDateAsc()
        val reports = ArrayList<Report>()
        for (i in 0..utilitiesData.size - 2) {
            val prevData = utilitiesData[i]
            val nextData = utilitiesData[i + 1]
            val date = nextData.date!!
            val tariff = tariffsByDate.stream()
                .filter { t -> t.date!! <= date }
                .findFirst()
                .orElseThrow { IllegalStateException("No tariff for date $date") }
            reports.add(Report(date, nextData, prevData, tariff))
        }
        return reports
    }
}