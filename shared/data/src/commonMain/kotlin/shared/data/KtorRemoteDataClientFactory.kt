package shared.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.reflect.*
import kotlinx.serialization.json.Json
import shared.domain.remote_data.RemoteDataClient
import shared.domain.remote_data.RemoteDataClientFactory
import shared.domain.remote_data.RemoteDataException

class KtorRemoteDataClientFactory : RemoteDataClientFactory {
    private val client by lazy {
        KtorRemoteDataClient()
    }

    override fun getClient(): RemoteDataClient<*> = client
}

class KtorRemoteDataClient : RemoteDataClient<HttpClient> {

    val httpClient: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    isLenient = true
                }

            )
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL
        }
        HttpResponseValidator {
            validateResponse { response ->
                val status = response.status
                if (status.value != 200) {
                    throw RemoteDataException(response.body(), status.value)
                }
            }
        }
    }

    override suspend fun <D : Any, E : Any> send(sendData: RemoteDataClient.SendData<D, E>): E = httpClient.post(
        sendData.to
    ) {
        contentType(ContentType.Application.Json)
        setBody(sendData.body, TypeInfo(sendData.body::class))
    }.body(TypeInfo(sendData.ret))

}

