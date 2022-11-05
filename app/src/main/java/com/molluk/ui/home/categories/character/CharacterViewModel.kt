package com.molluk.ui.home.categories.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.molluk.data.character.network.CharacterRepository
import com.molluk.ui.base.BaseViewModel
import com.molluk.ui.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    repository: CharacterRepository
) : BaseViewModel() {

    //get characters in page №
    private var _allCharactersInPage = MutableLiveData<Event<String>>()

    val allCharacterInPageResponse = _allCharactersInPage.switchMap { request ->
        repository.getAllCharacterInPage(request.peekContent())
    }

    fun getAllCharacterInPage(pageNum: Int) {
        _allCharactersInPage.value = Event("character/?page=$pageNum")
    }


    //get characters list
    private var _charactersList = MutableLiveData<Event<String>>()

    val charactersList = _charactersList.switchMap { request ->
        repository.getCharactersList(request.peekContent())
    }

    fun getCharactersList(characters: String) {
        _charactersList.value = Event("character/$characters")
    }

    fun getCharactersLink(link: String) {
        val id = link.substring(link.lastIndexOf("/"), link.length)
        _charactersList.value = Event("character/$id")
    }
}