package com.molluk.data.location.network

import androidx.lifecycle.liveData
import com.molluk.data.status.Resource
import com.molluk.ui.base.Event
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val network: LocationNetwork
) {

    fun getAllLocations() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = network.getAllLocations()
        if (source.status == Resource.Status.SUCCESS) {
            emit(Resource.success(source.response!!))
        } else if (source.status == Resource.Status.ERROR) {
            emit(Resource.error(source.error!!))
        }
    }

    fun getAllLocationInPage(link: String) = liveData(Dispatchers.IO) {
        emit(Event(Resource.loading()))
        val source = network.getAllLocationInPage(link)
        if (source.status == Resource.Status.SUCCESS) {
            emit(Event(Resource.success(source.response!!)))
        } else if (source.status == Resource.Status.ERROR) {
            emit(Event(Resource.error(source.error!!)))
        }
    }

    fun getLocation(link: String) = liveData(Dispatchers.IO) {
        emit(Event(Resource.loading()))
        val source = network.getLocation(link)
        if (source.status == Resource.Status.SUCCESS) {
            emit(Event(Resource.success(source.response!!)))
        } else if (source.status == Resource.Status.ERROR) {
            emit(Event(Resource.error(source.error!!)))
        }
    }
}