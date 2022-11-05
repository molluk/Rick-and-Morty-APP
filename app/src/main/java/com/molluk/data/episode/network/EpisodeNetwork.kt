package com.molluk.data.episode.network

import com.molluk.network.BaseNetwork
import javax.inject.Inject

class EpisodeNetwork @Inject constructor(
    private val service: EpisodeService
) : BaseNetwork() {

    suspend fun getAllEpisodes() = getResult {
        service.getAllEpisodes()
    }

    suspend fun getAllEpisodeInPage(link: String) = getResult {
        service.getAllEpisodeInPage(link)
    }

    suspend fun getEpisode(link: String) = getResult {
        service.getEpisode(link)
    }

}