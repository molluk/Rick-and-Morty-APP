package com.molluk.ui.home.categories.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.molluk.data.character.network.CharacterRepository
import com.molluk.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    repository: CharacterRepository
) : BaseViewModel() {

    val allCharactersResponse = repository.getAllCharacters()

    //get characters in page №
    private var _allCharactersInPage = MutableLiveData<String>()

    val allCharacterInPageResponse = _allCharactersInPage.switchMap { request ->
        repository.getAllCharacterInPage(request)
    }

    fun getAllCharacterInPage(pageNum: Int) {
        _allCharactersInPage.value = "character/?page=$pageNum"
    }
}