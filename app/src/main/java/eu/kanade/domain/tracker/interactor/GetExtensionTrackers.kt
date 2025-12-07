package eu.kanade.domain.tracker.interactor

import eu.kanade.tachiyomi.extension.model.Extension
import eu.kanade.tachiyomi.tracker.Tracker

class GetExtensionTrackers {

    fun get(extension: Extension.Installed): List<ExtensionTrackerItem> {
        val isMultiTracker = extension.trackers.size > 1

        return extension.trackers
            .map { tracker ->
                ExtensionTrackerItem(
                    tracker = tracker,
                    labelAsName = isMultiTracker,
                )
            }
    }
}

data class ExtensionTrackerItem(
    val tracker: Tracker,
    val labelAsName: Boolean,
)
