package com.sample.neugelb.data.model

import com.sample.neugelb.domain.model.Dates
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DatesData(
    val maximum: String,
    val minimum: String
)

internal fun DatesData.toDomain() =
    Dates(
        maximum = maximum,
        minimum = minimum
    )


