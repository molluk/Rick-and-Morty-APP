package com.molluk.data.character.network

import com.molluk.data.character.models.AllCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters(): Response<AllCharactersResponse>

    @GET
    suspend fun getAllCharacterInPage(
        @Url link: String
    ): Response<AllCharactersResponse>

}