package com.molluk.data.location.network

import com.molluk.data.location.models.AllLocationsResponse
import com.molluk.data.location.models.LocationResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface LocationService {

    @GET("character")
    suspend fun getAllLocations(): Response<AllLocationsResponse>

    @GET
    suspend fun getAllLocationInPage(
        @Url link: String
    ): Response<AllLocationsResponse>

    @GET
    suspend fun getLocation(
        @Url link: String
    ): Response<LocationResult>
}