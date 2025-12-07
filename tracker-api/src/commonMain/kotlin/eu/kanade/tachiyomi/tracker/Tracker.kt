package eu.kanade.tachiyomi.tracker

import eu.kanade.tachiyomi.tracker.model.TChapter
import eu.kanade.tachiyomi.tracker.model.TManga
import eu.kanade.tachiyomi.util.awaitSingle
import rx.Observable

/**
 * A basic interface for creating a tracker extension. Tracker extensions provide
 * information about manga/comic releases and chapters from tracking services.
 */
interface Tracker {

    /**
     * ID for the tracker. Must be unique.
     */
    val id: Long

    /**
     * Name of the tracker.
     */
    val name: String

    val lang: String
        get() = ""

    /**
     * Get the updated details for a manga from the tracker.
     *
     * @param manga the manga to update.
     * @return the updated manga.
     */
    @Suppress("DEPRECATION")
    suspend fun getMangaDetails(manga: TManga): TManga {
        return fetchMangaDetails(manga).awaitSingle()
    }

    /**
     * Get all the available chapters for a manga from the tracker.
     * Chapters may include source_url and scanlation_group fields to enable
     * opening them in the appropriate source extension.
     *
     * @param manga the manga to get chapters for.
     * @return the chapters for the manga.
     */
    @Suppress("DEPRECATION")
    suspend fun getChapterList(manga: TManga): List<TChapter> {
        return fetchChapterList(manga).awaitSingle()
    }

    @Deprecated(
        "Use the non-RxJava API instead",
        ReplaceWith("getMangaDetails"),
    )
    fun fetchMangaDetails(manga: TManga): Observable<TManga> =
        throw IllegalStateException("Not used")

    @Deprecated(
        "Use the non-RxJava API instead",
        ReplaceWith("getChapterList"),
    )
    fun fetchChapterList(manga: TManga): Observable<List<TChapter>> =
        throw IllegalStateException("Not used")
}
