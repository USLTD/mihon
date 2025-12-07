@file:Suppress("PropertyName")

package eu.kanade.tachiyomi.tracker.model

import kotlinx.serialization.Serializable

@Serializable
data class TMangaImpl(
    override var url: String = "",
    override var title: String = "",
    override var artist: String? = null,
    override var author: String? = null,
    override var description: String? = null,
    override var genre: String? = null,
    override var status: Int = 0,
    override var thumbnail_url: String? = null,
    override var initialized: Boolean = false,
) : TManga {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as TMangaImpl

        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}
