package com.achizhikov.kotlinutilitiesbot.database

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UtilitiesDataRepository : CrudRepository<UtilitiesData, Int> {
    fun findTop2ByOrderByDateDesc(): List<UtilitiesData>

    fun findAllByOrderByDateAsc(): List<UtilitiesData>
}

@Repository
interface TariffsRepository : CrudRepository<Tariff, Int> {
    fun findTopByOrderByDateDesc(): Optional<Tariff>

    fun findAllByOrderByDateDesc(): List<Tariff>
}