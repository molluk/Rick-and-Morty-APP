package com.molluk.data.location.models

data class AllLocationsResponse(
    val info: LocationInfo,
    val results: MutableList<LocationResult>
)

data class LocationInfo (
    val count: Long,
    val pages: Long,
    val next: String,
    val prev: Any? = null
)