package com.molluk.data.character.network

import androidx.lifecycle.liveData
import com.molluk.data.status.Resource
import com.molluk.ui.base.Event
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val network: CharacterNetwork
) {

    fun getAllCharacters() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = network.getAllCharacters()
        if (source.status == Resource.Status.SUCCESS) {
            emit(Resource.success(source.response!!))
        } else if (source.status == Resource.Status.ERROR) {
            emit(Resource.error(source.error!!))
        }
    }

    fun getAllCharacterInPage(link: String) = liveData(Dispatchers.IO) {
        emit(Event(Resource.loading()))
        val source = network.getAllCharacterInPage(link)
        if (source.status == Resource.Status.SUCCESS) {
            emit(Event(Resource.success(source.response!!)))
        } else if (source.status == Resource.Status.ERROR) {
            emit(Event(Resource.error(source.error!!)))
        }
    }
}