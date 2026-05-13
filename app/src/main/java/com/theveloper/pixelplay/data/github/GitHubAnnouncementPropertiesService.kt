package com.theveloper.pixelplay.data.github

import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Properties
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

data class PlayStoreAnnouncementRemoteConfig(
    val enabled: Boolean = false,
    val playStoreUrl: String? = null,
    val title: String? = null,
    val body: String? = null,
    val primaryActionLabel: String? = null,
    val dismissActionLabel: String? = null,
    val linkPendingMessage: String? = null,
)

@Singleton
class GitHubAnnouncementPropertiesService @Inject constructor() {

    /**
     * Reads announcement flags from a raw properties file in GitHub.
     *
     * Expected keys:
     * - play_store_announcement_enabled
     * - play_store_url
     * - play_store_announcement_title
     * - play_store_announcement_body
     * - play_store_primary_action
     * - play_store_dismiss_action
     * - play_store_link_pending_message
     */
    suspend fun fetchPlayStoreAnnouncement(
        owner: String = "theovilardo",
        repo: String = "PixelPlay",
        branch: String = "master",
        configPath: String = "remote-config/app-announcements.properties",
    ): Result<PlayStoreAnnouncementRemoteConfig> {
        return withContext(Dispatchers.IO) {
            runCatching {  PlayStoreAnnouncementRemoteConfig() }
        }
    }
}

private fun Properties.stringValue(key: String): String? {
    return getProperty(key)?.trim()?.takeIf { it.isNotEmpty() }
}

private fun Properties.booleanFlag(key: String): Boolean {
    return when (getProperty(key)?.trim()?.lowercase()) {
        "true", "1", "yes", "on" -> true
        else -> false
    }
}
