package com.molluk.data.character.models
import java.io.Serializable

data class CharacterResponseData(
    val data: CharacterResult
)

data class CharacterResult (
    val id: Long? = -1,
    val name: String? = "",
    val status: String? = "",
    val species: String? = "",
    val type: String? = "",
    val gender: String? = "",
    val origin: CharacterLocation? = null,
    val location: CharacterLocation? = null,
    val image: String? = "",
    val episode: MutableList<String>? = mutableListOf(),
    val url: String? = "",
    val created: String? = ""
): Serializable

data class CharacterLocation (
    val name: String? = "",
    val url: String? = ""
): Serializable