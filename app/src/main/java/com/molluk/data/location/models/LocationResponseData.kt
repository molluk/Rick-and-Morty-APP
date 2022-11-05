package com.molluk.data.location.models
import java.io.Serializable

data class LocationResponseData(
    val data: LocationResult
)

data class LocationResult (
    val id: Long,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: MutableList<String>,
    val url: String,
    val created: String
): Serializable
