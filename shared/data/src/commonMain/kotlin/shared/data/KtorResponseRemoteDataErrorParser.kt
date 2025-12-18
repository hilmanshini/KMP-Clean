package shared.data

import io.ktor.util.reflect.TypeInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.serializer
import shared.domain.remote_data.RemoteDataErrorParser
import shared.domain.remote_data.RemoteDataException
import shared.domain.remote_data.RemoteDataResponse
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Suppress("UNCHECKED_CAST")
class KtorResponseRemoteDataErrorParser(
) : RemoteDataErrorParser {

    private val errorJsonParser: Json = run {
        Json(builderAction = {
            ignoreUnknownKeys = true
        })
    }


    override fun <T, E : Any> mapResponse(
        result: Result<T>,
        returnClass: KType
    ): RemoteDataResponse<T, E> {
//        KType
        return if (result.isSuccess) {
            RemoteDataResponse.Success<T,E>(result.getOrThrow())
        } else {
            val ex = result.exceptionOrNull()

            if (ex is RemoteDataException) {
//                errorJsonParser.decodeFromString<>()
                val error =errorJsonParser.decodeFromString(deserializer = EmptySerializersModule().serializer(returnClass),ex.body) as E
                RemoteDataResponse.Failure<T,E>(error)
            } else {
                throw ex!!
            }
        }
    }
}
