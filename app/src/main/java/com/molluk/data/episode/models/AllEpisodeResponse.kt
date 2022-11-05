package com.molluk.data.episode.models

data class AllEpisodesResponse(
    val info: EpisodeInfo? = null,
    val results: MutableList<EpisodeResult>? = mutableListOf()
)

data class EpisodeInfo (
    val count: Long? = -1,
    val pages: Long? = -1,
    val next: String? = "",
    val prev: Any? = null
)