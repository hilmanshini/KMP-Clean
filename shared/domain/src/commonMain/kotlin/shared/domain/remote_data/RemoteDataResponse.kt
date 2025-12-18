package shared.domain.remote_data

sealed class RemoteDataResponse<out T, out E>() {
    class Failure<T, E>(val error: E) : RemoteDataResponse<Nothing, E>()
    class Success<T, E>(val succeed: T) : RemoteDataResponse<T, Nothing>()

    fun isSuccess(): Boolean = this is Success<*, *>
    fun isFailure(): Boolean = this is Failure<*,*>
}

class RemoteDataException(val body: String, val status: Int) : Exception()