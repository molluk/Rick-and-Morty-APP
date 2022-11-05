package com.molluk.data.character.models

data class AllCharactersResponse(
    val info: CharactersInfo? = null,
    val results: MutableList<CharacterResult>? = mutableListOf()
)

data class CharactersInfo (
    val count: Long? = -1,
    val pages: Long? = -1,
    val next: String? = "",
    val prev: Any? = null
)