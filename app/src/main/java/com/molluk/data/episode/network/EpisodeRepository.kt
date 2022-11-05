package com.molluk.data.episode.network

import androidx.lifecycle.liveData
import com.molluk.data.status.Resource
import com.molluk.ui.base.Event
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val network: EpisodeNetwork
) {

    fun getAllEpisodes() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = network.getAllEpisodes()
        if (source.status == Resource.Status.SUCCESS) {
            emit(Resource.success(source.response!!))
        } else if (source.status == Resource.Status.ERROR) {
            emit(Resource.error(source.error!!))
        }
    }

    fun getAllEpisodeInPage(link: String) = liveData(Dispatchers.IO) {
        emit(Event(Resource.loading()))
        val source = network.getAllEpisodeInPage(link)
        if (source.status == Resource.Status.SUCCESS) {
            emit(Event(Resource.success(source.response!!)))
        } else if (source.status == Resource.Status.ERROR) {
            emit(Event(Resource.error(source.error!!)))
        }
    }

    fun getEpisode(link: String) = liveData(Dispatchers.IO) {
        emit(Event(Resource.loading()))
        val source = network.getEpisode(link)
        if (source.status == Resource.Status.SUCCESS) {
            emit(Event(Resource.success(source.response!!)))
        } else if (source.status == Resource.Status.ERROR) {
            emit(Event(Resource.error(source.error!!)))
        }
    }

}