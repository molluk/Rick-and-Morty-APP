package com.molluk.data.episode.network

import com.molluk.data.episode.models.AllEpisodesResponse
import com.molluk.data.episode.models.EpisodeResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface EpisodeService {

    @GET("character")
    suspend fun getAllEpisodes(): Response<AllEpisodesResponse>

    @GET
    suspend fun getAllEpisodeInPage(
        @Url link: String
    ): Response<AllEpisodesResponse>

    @GET
    suspend fun getEpisode(
        @Url link: String
    ): Response<MutableList<EpisodeResult>>

}