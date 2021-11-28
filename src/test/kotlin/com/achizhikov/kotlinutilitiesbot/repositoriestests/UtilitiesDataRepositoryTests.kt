package com.achizhikov.kotlinutilitiesbot.repositoriestests

import com.achizhikov.kotlinutilitiesbot.database.UtilitiesDataRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UtilitiesDataRepositoryTests {
    @Autowired
    private lateinit var utilitiesDataRepository: UtilitiesDataRepository

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
}
