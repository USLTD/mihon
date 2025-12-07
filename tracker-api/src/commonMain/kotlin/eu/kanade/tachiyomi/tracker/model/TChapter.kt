@file:Suppress("PropertyName")

package eu.kanade.tachiyomi.tracker.model

import java.io.Serializable

interface TChapter : Serializable {

    var url: String

    var name: String

    var date_upload: Long

    var chapter_number: Float

    var scanlator: String?

    /**
     * URL that can be used to open this chapter in a source extension.
     * This is used when a tracker returns chapters from multiple scanlation groups.
     */
    var source_url: String?

    /**
     * The name or identifier of the scanlation group that released this chapter.
     * This helps identify which source extension should handle this chapter.
     */
    var scanlation_group: String?

    fun copyFrom(other: TChapter) {
        name = other.name
        url = other.url
        date_upload = other.date_upload
        chapter_number = other.chapter_number
        scanlator = other.scanlator
        source_url = other.source_url
        scanlation_group = other.scanlation_group
    }

    companion object {
        fun create(): TChapter {
            return TChapterImpl()
        }
    }
}
