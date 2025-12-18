package auth.data.test

import auth.data.data_source.credential.CredentialRemoteDataSourceFirebase
import auth.data.data_source.credential.data.FirebaseAuthResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import shared.domain.DesktopPlatformConfig
import shared.domain.DesktopPlatformConfigRepository
import shared.domain.remote_data.RemoteDataClient
import shared.domain.remote_data.RemoteDataClientFactory
import shared.domain.remote_data.RemoteDataErrorParser
import shared.domain.remote_data.RemoteDataResponse
import shared.registerSharedModuleCommon
import shared.registerSharedModuleDesktop
import kotlin.test.BeforeTest
import kotlin.test.assertNotNull

class CredentialRemoteDataSourceTest {
    val remoteDataClientFactory: RemoteDataClientFactory by inject(RemoteDataClientFactory::class.java)
    val platformConfigRepository: DesktopPlatformConfigRepository by inject(DesktopPlatformConfigRepository::class.java)
    val remoteParser: RemoteDataErrorParser by inject(RemoteDataErrorParser::class.java)
    var platformConfig: DesktopPlatformConfig? = null
    val httpClient: RemoteDataClient<*> by lazy {
        remoteDataClientFactory.getClient()
    }

    @BeforeTest
    fun setup() {
        startKoin {
            registerSharedModuleCommon()
            registerSharedModuleDesktop()
        }
        runBlocking {
            platformConfig = platformConfigRepository.getPlatformConfig(DesktopPlatformConfigRepository.AUTH)
        }
    }

    inline fun <T> runAsResult(block: () -> T): Result<T> {
        return runCatching {
            block()
        }
    }

    @Test
    fun testSignIn() {
        runBlocking {
            val response = CredentialRemoteDataSourceFirebase(
                remoteDataClientFactory,

                platformConfigRepository,
                remoteParser

            ).run {
                fetchConfig()
                login("test123@googl.com", "test123!!33")
            }
            println(response)
        }
    }


    @Test
    fun testSignUp() {
        runBlocking {
            val response = CredentialRemoteDataSourceFirebase(
                remoteDataClientFactory,

                platformConfigRepository,
                remoteParser

            ).run {
                fetchConfig()
                signUp("test123@googl.com", "test123!!33")
            }
            println(response)
        }
    }

    @Test
    fun testLookup() {
        runBlocking {
            val responseLogin = CredentialRemoteDataSourceFirebase(
                remoteDataClientFactory,

                platformConfigRepository,
                remoteParser

            ).run {
                fetchConfig()
                login("test123@googl.com", "test123!!33")
            }
            assert(responseLogin.isSuccess())
            val token = (responseLogin as RemoteDataResponse.Success<FirebaseAuthResponse, *>).succeed.idToken
            assertNotNull(token)
            val response = CredentialRemoteDataSourceFirebase(
                remoteDataClientFactory,

                platformConfigRepository,
                remoteParser

            ).run {
                fetchConfig()
                validate(token)
            }
            println(response)
        }
    }

}