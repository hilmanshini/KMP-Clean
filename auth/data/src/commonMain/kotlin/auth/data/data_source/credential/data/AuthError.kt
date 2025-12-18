package auth.data.data_source.credential.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseAuthErrorResponse(
    @SerialName("error")
    val error: Error
)

@Serializable
data class ErrorItem(
    @SerialName("domain")
    val domain: String,
    @SerialName("message")
    val message: String,
    @SerialName("reason")
    val reason: String
)

@Serializable
data class Error(
    @SerialName("code")
    val code: Int,
    @SerialName("errors")
    val errors: List<ErrorItem>,
    @SerialName("message")
    val message: String
)