package com.molluk.ui.home.categories.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.molluk.data.episode.network.EpisodeRepository
import com.molluk.ui.base.BaseViewModel
import com.molluk.ui.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    repository: EpisodeRepository
) : BaseViewModel() {

    //get episode in page №
    private var _allEpisodesInPage = MutableLiveData<Event<String>>()

    val allEpisodeInPageResponse = _allEpisodesInPage.switchMap { request ->
        repository.getAllEpisodeInPage(request.peekContent())
    }

    fun getAllEpisodeInPage(pageNum: Int) {
        _allEpisodesInPage.value = Event("episode/?page=$pageNum")
    }


    private var _episode = MutableLiveData<Event<String>>()

    val episodeResponse = _episode.switchMap { request ->
        repository.getEpisode(request.peekContent())
    }

    fun episode(link: String) {
        _episode.value = Event(link)
    }

    fun getEpisodeList(listId: String) {
        _episode.value = Event("episode/$listId")
    }

}