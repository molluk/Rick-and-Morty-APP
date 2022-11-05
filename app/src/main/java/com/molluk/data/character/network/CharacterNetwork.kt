package com.molluk.data.character.network

import com.molluk.network.BaseNetwork
import javax.inject.Inject

class CharacterNetwork @Inject constructor(
    private val service: CharacterService
) : BaseNetwork() {

    suspend fun getAllCharacters() = getResult {
        service.getAllCharacters()
    }

    suspend fun getAllCharacterInPage(link: String) = getResult {
        service.getAllCharacterInPage(link)
    }

    suspend fun getCharactersList(link: String) = getResult {
        service.getCharactersList(link)
    }
}