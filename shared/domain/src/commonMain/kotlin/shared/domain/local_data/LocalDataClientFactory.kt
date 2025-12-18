package shared.domain.local_data

interface LocalDataClientFactory {

    fun createClient(): LocalDataClient
}

interface LocalDataClient{

}