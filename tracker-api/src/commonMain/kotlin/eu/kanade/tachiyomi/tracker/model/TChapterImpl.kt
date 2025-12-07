@file:Suppress("PropertyName")

package eu.kanade.tachiyomi.tracker.model

import kotlinx.serialization.Serializable

@Serializable
data class TChapterImpl(
    override var url: String = "",
    override var name: String = "",
    override var date_upload: Long = 0,
    override var chapter_number: Float = -1f,
    override var scanlator: String? = null,
    override var source_url: String? = null,
    override var scanlation_group: String? = null,
) : TChapter {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as TChapterImpl

        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}
