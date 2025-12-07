package eu.kanade.domain.chapter.service

import eu.kanade.tachiyomi.extension.ExtensionManager
import eu.kanade.tachiyomi.source.Source
import eu.kanade.tachiyomi.source.online.HttpSource
import eu.kanade.tachiyomi.source.online.ResolvableSource
import eu.kanade.tachiyomi.source.online.UriType
import tachiyomi.domain.chapter.model.Chapter
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Service for resolving chapters from tracker extensions to their corresponding source extensions.
 * 
 * Resolution strategy:
 * 1. Deep linking - Check if any ResolvableSource can handle the URL
 * 2. Scanlator name matching - Match by scanlator name if available
 * 3. Base URL matching - Fallback to comparing base URLs
 */
class ChapterSourceResolver(
    private val extensionManager: ExtensionManager = Injekt.get(),
) {

    /**
     * Finds a source that can handle the given chapter's sourceUrl.
     * This is useful for chapters coming from tracker extensions that reference
     * multiple source extensions.
     *
     * Uses a multi-strategy approach:
     * 1. Deep linking via ResolvableSource (primary)
     * 2. Scanlator name matching (fallback)
     * 3. Base URL matching (final fallback)
     *
     * @param chapter The chapter with a sourceUrl to resolve
     * @return The source that can handle this chapter, or null if none found
     */
    fun findSourceForChapter(chapter: Chapter): Source? {
        val sourceUrl = chapter.sourceUrl ?: return null
        
        // Get all installed sources
        val installedSources = extensionManager.installedExtensionsFlow.value
            .flatMap { it.sources }
        
        // Strategy 1: Try deep linking first (most reliable)
        val resolvableSource = installedSources
            .filterIsInstance<ResolvableSource>()
            .firstOrNull { source ->
                val uriType = try {
                    source.getUriType(sourceUrl)
                } catch (e: Exception) {
                    UriType.Unknown
                }
                uriType == UriType.Chapter || uriType == UriType.Manga
            }
        
        if (resolvableSource != null) {
            return resolvableSource
        }
        
        // Strategy 2: Try matching by scanlator name (if available)
        val scanlator = chapter.scanlator
        if (!scanlator.isNullOrBlank()) {
            val sourceByScanlator = installedSources.firstOrNull { source ->
                // Match if source name contains scanlator or vice versa (case-insensitive)
                source.name.contains(scanlator, ignoreCase = true) ||
                    scanlator.contains(source.name, ignoreCase = true)
            }
            
            if (sourceByScanlator != null) {
                return sourceByScanlator
            }
        }
        
        // Strategy 3: Fallback to base URL matching (least reliable)
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
