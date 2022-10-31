package com.molluk.data.character.models

data class AllCharactersResponse(
    val info: CharactersInfo,
    val results: MutableList<CharacterResult>
)

data class CharactersInfo (
    val count: Long,
    val pages: Long,
    val next: String,
    val prev: Any? = null
)