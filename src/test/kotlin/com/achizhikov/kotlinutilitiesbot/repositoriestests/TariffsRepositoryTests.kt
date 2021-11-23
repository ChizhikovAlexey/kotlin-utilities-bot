package com.achizhikov.kotlinutilitiesbot.repositoriestests

import com.achizhikov.kotlinutilitiesbot.Tariff
import com.achizhikov.kotlinutilitiesbot.TariffsRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TariffsRepositoryTests {
    @Autowired
    private lateinit var tariffsRepository: TariffsRepository

    private val firstDate = LocalDate.of(2020, 1,1)
    private val secondDate = LocalDate.of(2020, 2,2)
    private val olderTariff = Tariff(null, firstDate, 1.1, 2.2, 3.3, 0.0)
    private val newerTariff = Tariff(null, firstDate, 1.1, 2.2, 3.3, 0.0)


    @BeforeEach
    @AfterAll
    fun prepare() {
        tariffsRepository.deleteAll()
    }

    @Test
    fun deleteTest() {
        tariffsRepository.deleteAll()
        Assertions.assertEquals(0, tariffsRepository.findAll().count())
    }

    @Test
    fun saveAndFindTest() {
        val savedTariff = tariffsRepository.save(newerTariff)
        Assertions.assertNotNull(savedTariff)
        Assertions.assertNotNull(savedTariff.id)
        val selectedTariffOptional = tariffsRepository.findById(savedTariff.id!!)
        Assertions.assertTrue(selectedTariffOptional.isPresent)
        val selectedTariff = selectedTariffOptional.get()
        Assertions.assertTrue(selectedTariff.equalsIgnoringId(newerTariff))
    }

    @Test
    fun findByDateTest() {
        tariffsRepository.saveAll(listOf(newerTariff, olderTariff))
        val selectedTariffOptional = tariffsRepository.findByDate(firstDate)
        Assertions.assertTrue(selectedTariffOptional.isPresent)
        val selectedTariff = selectedTariffOptional.get()
        Assertions.assertTrue(selectedTariff.equalsIgnoringId(newerTariff))
    }

    fun Tariff.equalsIgnoringId(that: Any): Boolean {
        if (this === that) return true
        if (that !is Tariff) return false
        if (this.date != that.date) return false
        if (this.electricity != that.electricity) return false
        if (this.hotWater != that.hotWater) return false
        if (this.coldWater != that.coldWater) return false
        if (this.drainage != that.drainage) return false
        return true
    }
}
