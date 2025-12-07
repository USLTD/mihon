package eu.kanade.tachiyomi.tracker

/**
 * A factory for creating multiple tracker instances from a single extension.
 */
interface TrackerFactory {

    /**
     * Creates a list of trackers from this factory.
     */
    fun createTrackers(): List<Tracker>
}
