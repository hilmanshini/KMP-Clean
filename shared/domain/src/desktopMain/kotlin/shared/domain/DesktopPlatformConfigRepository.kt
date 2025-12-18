package shared.domain

interface DesktopPlatformConfigRepository {
    suspend fun getPlatformConfig(name:String): DesktopPlatformConfig?

    companion object {
        val AUTH = "AUTH_PLATFORM"
    }
}

interface DesktopPlatformConfig{
    companion object {
        const val API_KEY = "API_KEY"
        const val AUTH_DOMAIN = "AUTH_DOMAIN"
        const val PROJECT_ID = "PROJECT_ID"
        const val STORAGE_BUCKET = "STORAGE_BUCKET"
        const val MESSAGING_SENDER_ID = "MESSAGING_SENDER_ID"
        const val APP_ID = "APP_ID"
        const val MEASUREMENT_ID = "MEASUREMENT_ID"
    }
    fun getStringValue(name:String): String?
}