package com.achizhikov.kotlinutilitiesbot.repositoriestests

import com.achizhikov.kotlinutilitiesbot.UtilitiesData
import com.achizhikov.kotlinutilitiesbot.UtilitiesDataRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UtilitiesDataRepositoryTests {
    @Autowired
    private lateinit var utilitiesDataRepository: UtilitiesDataRepository

    private val firstDate = LocalDate.of(2020, 1, 1)
    private val secondDate = LocalDate.of(2020, 2, 2)
    private val newerData = UtilitiesData(null, firstDate, 0, 0, 0, 0, 0)
    private val olderData = UtilitiesData(null, secondDate, 100, 10, 20, 10, 20)

    @BeforeEach
    @AfterAll
    fun prepare() {
        utilitiesDataRepository.deleteAll()
    }

    @Test
    fun deleteTest() {

        utilitiesDataRepository.deleteAll()
        Assertions.assertEquals(0, utilitiesDataRepository.findAll().count())
    }

    @Test
    fun saveAndFindTest() {
        val savedData = utilitiesDataRepository.save(newerData)
        Assertions.assertNotNull(savedData)
        Assertions.assertNotNull(savedData.id)
        val selectedDataOptional = utilitiesDataRepository.findById(savedData.id!!)
        Assertions.assertTrue(selectedDataOptional.isPresent)
        val selectedData = selectedDataOptional.get()
        Assertions.assertTrue(selectedData.equalsIgnoringId(newerData))
    }

    @Test
    fun findByDateTest() {
        utilitiesDataRepository.saveAll(listOf(newerData, olderData))
        val selectedDataOptional = utilitiesDataRepository.findByDate(firstDate)
        Assertions.assertTrue(selectedDataOptional.isPresent)
        val selectedData = selectedDataOptional.get()
        Assertions.assertTrue(selectedData.equalsIgnoringId(newerData))
    }

    fun UtilitiesData.equalsIgnoringId(that: Any): Boolean {
        if (this === that) return true
        if (that !is UtilitiesData) return false
        if (this.date != that.date) return false
        if (this.electricity != that.electricity) return false
        if (this.bathColdWater != that.bathColdWater) return false
        if (this.bathHotWater != that.bathHotWater) return false
        if (this.kitchenHotWater != that.kitchenHotWater) return false
        if (this.kitchenColdWater != that.kitchenColdWater) return false
        return true
    }
}
