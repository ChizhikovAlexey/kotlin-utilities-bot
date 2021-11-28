package com.achizhikov.kotlinutilitiesbot.repositoriestests

import com.achizhikov.kotlinutilitiesbot.database.TariffsRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TariffsRepositoryTests {
    @Autowired
    private lateinit var tariffsRepository: TariffsRepository


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
}
