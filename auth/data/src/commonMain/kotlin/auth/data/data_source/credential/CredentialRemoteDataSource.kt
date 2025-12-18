package auth.data.data_source.credential

import auth.data.data_source.credential.data.FirebaseAuthErrorResponse
import auth.data.data_source.credential.data.FirebaseAuthResponse
import auth.data.data_source.credential.data.LookupResponse
import shared.domain.remote_data.RemoteDataResponse

interface CredentialRemoteDataSource {


    suspend fun login(email: String, password: String): RemoteDataResponse<FirebaseAuthResponse, FirebaseAuthErrorResponse>

    suspend fun signUp(email: String, password: String): RemoteDataResponse<Pair<String,String>, FirebaseAuthErrorResponse>

    suspend fun validate(key: String):RemoteDataResponse<LookupResponse, FirebaseAuthErrorResponse>

    suspend fun fetchConfig(): Result<*>

}