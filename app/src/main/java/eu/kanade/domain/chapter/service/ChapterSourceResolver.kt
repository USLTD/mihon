package eu.kanade.domain.chapter.service

import eu.kanade.tachiyomi.extension.ExtensionManager
import eu.kanade.tachiyomi.source.Source
import eu.kanade.tachiyomi.source.online.HttpSource
import tachiyomi.domain.chapter.model.Chapter
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Service for resolving chapters from tracker extensions to their corresponding source extensions.
 */
class ChapterSourceResolver(
    private val extensionManager: ExtensionManager = Injekt.get(),
) {

    /**
     * Finds a source that can handle the given chapter's sourceUrl.
     * This is useful for chapters coming from tracker extensions that reference
     * multiple source extensions.
     *
     * @param chapter The chapter with a sourceUrl to resolve
     * @return The source that can handle this chapter, or null if none found
     */
    fun findSourceForChapter(chapter: Chapter): Source? {
        val sourceUrl = chapter.sourceUrl ?: return null
        
        // Get all installed sources
        val installedSources = extensionManager.installedExtensionsFlow.value
            .flatMap { it.sources }
        
        // Try to find a source that can handle this URL
        return installedSources.firstOrNull { source ->
            when (source) {
                is HttpSource -> {
                    // Check if the sourceUrl starts with the source's base URL
                    sourceUrl.startsWith(source.baseUrl, ignoreCase = true)
                }
                else -> false
            }
        }
    }

    /**
     * Checks if a chapter has a sourceUrl that can be resolved to an installed source.
     *
     * @param chapter The chapter to check
     * @return true if the chapter can be opened in a source extension
     */
    fun canResolveToSource(chapter: Chapter): Boolean {
        return findSourceForChapter(chapter) != null
    }
}
