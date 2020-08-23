package com.sample.neugelb.data.model

import com.sample.neugelb.DataFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class DatesDataTest {

    @Test
    fun `dates data to domain`() {
        val datesData = DataFixtures.datesData
        val dates = DataFixtures.dates

        assertEquals(dates.maximum, datesData.toDomain().maximum)
        assertEquals(dates.minimum, datesData.toDomain().minimum)
    }
}