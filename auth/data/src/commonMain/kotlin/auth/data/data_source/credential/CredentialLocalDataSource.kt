package auth.data.data_source.credential

internal interface CredentialLocalDataSource {
    fun saveToken(token: String)
}