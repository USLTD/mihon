package eu.kanade.tachiyomi.tracker.model

import kotlinx.serialization.Serializable

@Serializable
data class MangasPage(val mangas: List<TManga>, val hasNextPage: Boolean)
