package shared
//import io.
import org.koin.core.KoinApplication
import org.koin.dsl.module
import shared.data.KtorRemoteDataClientFactory
import shared.data.KtorResponseRemoteDataErrorParser
import shared.domain.remote_data.RemoteDataClientFactory
import shared.domain.remote_data.RemoteDataErrorParser

fun KoinApplication.registerSharedModuleCommon() = modules(
    module {

        single<RemoteDataClientFactory> { KtorRemoteDataClientFactory() }
        single<RemoteDataErrorParser> { KtorResponseRemoteDataErrorParser() }
    }
)