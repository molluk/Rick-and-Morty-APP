package com.molluk.data.location.network

import com.molluk.network.BaseNetwork
import javax.inject.Inject

class LocationNetwork @Inject constructor(
    private val service: LocationService
) : BaseNetwork() {

    suspend fun getAllLocations() = getResult {
        service.getAllLocations()
    }

    suspend fun getAllLocationInPage(link: String) = getResult {
        service.getAllLocationInPage(link)
    }

    suspend fun getLocation(link: String) = getResult {
        service.getLocation(link)
    }
}