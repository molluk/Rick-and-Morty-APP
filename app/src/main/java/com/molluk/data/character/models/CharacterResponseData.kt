package com.molluk.data.character.models
import java.io.Serializable

data class CharacterResponseData(
    val data: CharacterResult
)

data class CharacterResult (
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterLocation,
    val location: CharacterLocation,
    val image: String,
    val episode: MutableList<String>,
    val url: String,
    val created: String
): Serializable

data class CharacterLocation (
    val name: String,
    val url: String
): Serializable