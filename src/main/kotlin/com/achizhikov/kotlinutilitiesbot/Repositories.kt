package com.achizhikov.kotlinutilitiesbot

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface UtilitiesDataRepository : CrudRepository<UtilitiesData, Int> {
    @Cacheable("utilities_data")
    @Query(
        """
            SELECT *
            FROM utilities_data
            WHERE date <= :date
            ORDER BY date DESC
            LIMIT 1
        """
    )
    fun findByDate(date: LocalDate): Optional<UtilitiesData>
}

@Repository
interface TariffsRepository : CrudRepository<Tariff, Int> {
    @Cacheable("tariff")
    @Query(
        """
            SELECT *
            FROM tariffs
            WHERE date <= :date
            ORDER BY date DESC
            LIMIT 1
        """
    )
    fun findByDate(date: LocalDate): Optional<Tariff>
}