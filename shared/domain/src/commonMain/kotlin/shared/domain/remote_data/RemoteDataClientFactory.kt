package shared.domain.remote_data

import kotlin.reflect.KClass

interface RemoteDataClientFactory {
    fun getClient(): RemoteDataClient<*>
}

interface RemoteDataClient<T> {

    suspend fun <D : Any, E : Any> send(sendData: SendData<D, E>) :E


    data class SendData<B : Any, R : Any>(
        val to: String,
        val body: B,
        val ret: KClass<R>
    )
}
