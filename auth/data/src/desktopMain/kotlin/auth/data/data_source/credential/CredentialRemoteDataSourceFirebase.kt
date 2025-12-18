package auth.data.data_source.credential

import auth.data.data_source.credential.data.*
import shared.domain.DesktopPlatformConfig
import shared.domain.DesktopPlatformConfigRepository
import shared.domain.remote_data.RemoteDataClient
import shared.domain.remote_data.RemoteDataClientFactory
import shared.domain.remote_data.RemoteDataErrorParser
import shared.domain.remote_data.RemoteDataResponse
import kotlin.reflect.typeOf

class CredentialRemoteDataSourceFirebase(
    remoteDataClientFactory: RemoteDataClientFactory,
    val desktopPlatformConfigRepository: DesktopPlatformConfigRepository,
    val remoteDataErrorParser: RemoteDataErrorParser
) : CredentialRemoteDataSource {
    private val client = remoteDataClientFactory.getClient()
    private var config: DesktopPlatformConfig? = null
    private val apiKey by lazy {
        if (config == null) {
            throw Exception("Config not loaded. Call fetchConfig() first.")
        }
        config?.getStringValue(DesktopPlatformConfig.API_KEY)
    }

    override suspend fun login(
        email: String,
        password: String
    ): RemoteDataResponse<FirebaseAuthResponse, FirebaseAuthErrorResponse> = runCatching {
        client.send(
            RemoteDataClient.SendData(
                to = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$apiKey", body =
                    FirebaseAuthRequest(email, password, true), FirebaseAuthResponse::class
            )
        )
    }.run {
        remoteDataErrorParser.mapResponse(this, typeOf<FirebaseAuthErrorResponse>())
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): RemoteDataResponse<Pair<String, String>, FirebaseAuthErrorResponse> = runCatching {
        client.send(
            RemoteDataClient.SendData(
                to = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$apiKey", body =
                    FirebaseAuthSignUpRequest(email, password, true), FIrebaseAuthSignUpResponse::class
            )
        )
    }.map {
        it.idToken to it.refreshToken
    }.run {
        remoteDataErrorParser.mapResponse(this, typeOf<FirebaseAuthErrorResponse>())
    }

    override suspend fun validate(key: String): RemoteDataResponse<LookupResponse, FirebaseAuthErrorResponse> =
        runCatching {
            client.send(
                RemoteDataClient.SendData(
                    to = "https://identitytoolkit.googleapis.com/v1/accounts:lookup?key=$apiKey",
                    body = LookupData(key),
                    ret = LookupResponse::class
                )
            )
        }.run {
            remoteDataErrorParser.mapResponse(this, typeOf<FirebaseAuthErrorResponse>())
        }

    override suspend fun fetchConfig(): Result<*> = runCatching {
        this.config = desktopPlatformConfigRepository.getPlatformConfig(DesktopPlatformConfigRepository.AUTH)
            ?: throw Exception("Fetch config failure")
    }
}

