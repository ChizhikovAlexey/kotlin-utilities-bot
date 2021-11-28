package com.achizhikov.kotlinutilitiesbot.repositoriestests

import com.achizhikov.kotlinutilitiesbot.database.DataService
import com.achizhikov.kotlinutilitiesbot.database.TariffsRepository
import com.achizhikov.kotlinutilitiesbot.database.UtilitiesDataRepository
import com.achizhikov.kotlinutilitiesbot.reports.excel.createExcel
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExcelTests {
    @Autowired
    lateinit var dataService: DataService

    @Autowired
    lateinit var tariffsRepository: TariffsRepository

    @Autowired
    lateinit var utilitiesDataRepository: UtilitiesDataRepository

    @BeforeEach
    @AfterAll
    fun prepare() {
        tariffsRepository.deleteAll()
        utilitiesDataRepository.deleteAll()
    }

    @Test
    fun findAllUtilitiesDataWithTariffsTest() {
        tariffsRepository.saveAll(listOf(olderTariff, newerTariff))
        utilitiesDataRepository.saveAll(listOf(olderData, newerData))
        val result = dataService.getAllReports()
        Assertions.assertEquals(1, result.size)
        Assertions.assertEquals(secondDate, result[0].date)
        Assertions.assertTrue(result[0].tariff.equalsIgnoringId(olderTariff))
        Assertions.assertTrue(olderData.equalsIgnoringId(result[0].olderData))
        Assertions.assertTrue(newerData.equalsIgnoringId(result[0].newerData))
    }

    @Test
    fun excelIsGeneratedTest() {
        tariffsRepository.saveAll(listOf(olderTariff, newerTariff))
        utilitiesDataRepository.saveAll(listOf(olderData, newerData))
        val result = dataService.getAllReports()
        createExcel(result)
    }
}