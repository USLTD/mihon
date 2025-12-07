package eu.kanade.tachiyomi.extension.model

import android.graphics.drawable.Drawable
import eu.kanade.tachiyomi.source.Source
import eu.kanade.tachiyomi.tracker.Tracker
import tachiyomi.domain.source.model.StubSource

sealed class Extension {

    abstract val name: String
    abstract val pkgName: String
    abstract val versionName: String
    abstract val versionCode: Long
    abstract val libVersion: Double
    abstract val lang: String?
    abstract val isNsfw: Boolean

    enum class Type {
        SOURCE,
        TRACKER
    }

    data class Installed(
        override val name: String,
        override val pkgName: String,
        override val versionName: String,
        override val versionCode: Long,
        override val libVersion: Double,
        override val lang: String,
        override val isNsfw: Boolean,
        val pkgFactory: String?,
        val sources: List<Source>,
        val trackers: List<Tracker>,
        val icon: Drawable?,
        val hasUpdate: Boolean = false,
        val isObsolete: Boolean = false,
        val isShared: Boolean,
        val repoUrl: String? = null,
    ) : Extension() {
        val type: Type
            get() = when {
                trackers.isNotEmpty() -> Type.TRACKER
                else -> Type.SOURCE
            }
    }

    data class Available(
        override val name: String,
        override val pkgName: String,
        override val versionName: String,
        override val versionCode: Long,
        override val libVersion: Double,
        override val lang: String,
        override val isNsfw: Boolean,
        val sources: List<Source>,
        val trackers: List<Tracker>,
        val apkName: String,
        val iconUrl: String,
        val repoUrl: String,
    ) : Extension() {

        val type: Type
            get() = when {
                trackers.isNotEmpty() -> Type.TRACKER
                else -> Type.SOURCE
            }

        data class Source(
            val id: Long,
            val lang: String,
            val name: String,
            val baseUrl: String,
        ) {
            fun toStubSource(): StubSource {
                return StubSource(
                    id = this.id,
                    lang = this.lang,
                    name = this.name,
                )
            }
        }

        data class Tracker(
            val id: Long,
            val lang: String,
            val name: String,
        )
    }

    data class Untrusted(
        override val name: String,
        override val pkgName: String,
        override val versionName: String,
        override val versionCode: Long,
        override val libVersion: Double,
        val signatureHash: String,
        override val lang: String? = null,
        override val isNsfw: Boolean = false,
    ) : Extension()
}
