package eu.kanade.tachiyomi.tracker

/**
 * A configurable tracker supports a settings menu controlled by the user.
 */
interface ConfigurableTracker {

    /**
     * Gets the Compose Preference screen for this tracker.
     */
    fun setupPreferenceScreen(screen: PreferenceScreen)
}
