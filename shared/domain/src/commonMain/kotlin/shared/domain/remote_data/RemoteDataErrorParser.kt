package shared.domain.remote_data

import kotlin.reflect.KClass
import kotlin.reflect.KType

interface RemoteDataErrorParser {

//    fun <T, E> mapResponse(
//        result: Result<T>,
//        errorParser: (String) -> E
//    ):RemoteDataResponse<T, E>
    fun <T, E : Any> mapResponse(
        result: Result<T>,
        returnClass: KType
    ):RemoteDataResponse<T, E>
}
