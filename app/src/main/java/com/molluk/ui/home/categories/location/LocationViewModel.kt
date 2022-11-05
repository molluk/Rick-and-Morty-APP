package com.molluk.ui.home.categories.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.molluk.data.location.network.LocationRepository
import com.molluk.ui.base.BaseViewModel
import com.molluk.ui.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    repository: LocationRepository
) : BaseViewModel() {

    //getLocation in page №
    private var _allLocationsInPage = MutableLiveData<Event<String>>()

    val allLocationInPageResponse = _allLocationsInPage.switchMap { request ->
        repository.getAllLocationInPage(request.peekContent())
    }

    fun getAllLocationInPage(pageNum: Int) {
        _allLocationsInPage.value = Event("location/?page=$pageNum")
    }


    private var _location = MutableLiveData<Event<String>>()

    val locationResponse = _location.switchMap { request ->
        repository.getLocation(request.peekContent())
    }

    fun location(link: String) {
        _location.value = Event(link)
    }

    fun getLocationList(listId: String) {
        _location.value = Event("location/$listId")
    }
}