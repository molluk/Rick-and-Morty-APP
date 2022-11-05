package com.molluk.data.episode.models
import com.squareup.moshi.Json
import java.io.Serializable

data class EpisodeResponseData(
    val data: EpisodeResult? = null
)

data class EpisodeResult (
    val id: Long? = -1,
    val name: String? = "",
    @field:Json(name = "air_date")
    val airDate: String? = "",
    val episode: String? = "",
    val characters: MutableList<String>? = mutableListOf(),
    val url: String? = "",
    val created: String? = ""
): Serializable
