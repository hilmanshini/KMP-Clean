package shared.data.firebase

import shared.domain.DesktopPlatformConfig
import shared.domain.DesktopPlatformConfig.Companion.APP_ID
import shared.domain.DesktopPlatformConfig.Companion.AUTH_DOMAIN
import shared.domain.DesktopPlatformConfig.Companion.MEASUREMENT_ID
import shared.domain.DesktopPlatformConfig.Companion.MESSAGING_SENDER_ID
import shared.domain.DesktopPlatformConfig.Companion.PROJECT_ID
import shared.domain.DesktopPlatformConfig.Companion.STORAGE_BUCKET
import shared.domain.DesktopPlatformConfigRepository
import shared.domain.DesktopPlatformConfigRepository.Companion.AUTH

class DesktopPlatformConfigRepositoryFirebase : DesktopPlatformConfigRepository {

    private val map = mutableMapOf<String, DesktopPlatformConfig>()


    init {
        map[AUTH] = DesktopAuthPlatformConfig()
    }

    override suspend fun getPlatformConfig(name: String): DesktopPlatformConfig? =
        map[name]

}

/***
 * TODO: MAKE IT REMOTE FETCH
 */
class DesktopAuthPlatformConfig : DesktopPlatformConfig {
    private val configMap = mapOf(
            DesktopPlatformConfig.Companion.API_KEY to "AIzaSyAYx-9F8Mi0LZKhavgP1wO5UE2v1e2mYWo",
            AUTH_DOMAIN to "newscopy-155d8.firebaseapp.com",
            PROJECT_ID to "newscopy-155d8",
            STORAGE_BUCKET to "newscopy-155d8.firebasestorage.app",
            MESSAGING_SENDER_ID to "979171853482",
            APP_ID to "1:979171853482:web:73623a6f728dcac72afcd3",
            MEASUREMENT_ID to "G-NXRQFNQT23"
        )

    //    private const val AUTH_DOMAIN = "AUTH_DOMAIN"

    override fun getStringValue(name: String): String? = configMap[name]
}