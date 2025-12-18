package auth.data.data_source.credential.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class FirebaseAuthSignUpRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)

@Serializable
internal data class FirebaseAuthRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)

@Serializable
data class FIrebaseAuthSignUpResponse(
    @SerialName("email")
    val email: String,
    @SerialName("expiresIn")
    val expiresIn: String,
    @SerialName("idToken")
    val idToken: String,
    @SerialName("kind")
    val kind: String,
    @SerialName("localId")
    val localId: String,
    @SerialName("refreshToken")
    val refreshToken: String
)

@Serializable
data class FirebaseErrorResponse(
    val error: FirebaseError
)

@Serializable
data class FirebaseError(
    val message: String
)


@Serializable
data class FirebaseAuthResponse(
    @SerialName("displayName")
    val displayName: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("expiresIn")
    val expiresIn: String? = null,
    @SerialName("idToken")
    val idToken: String? = null,
    @SerialName("kind")
    val kind: String? = null,
    @SerialName("localId")
    val localId: String? = null,
    @SerialName("mfaPendingCredential")
    val mfaPendingCredential: String? = null,
    @SerialName("oauthAccessToken")
    val oauthAccessToken: String? = null,
    @SerialName("oauthAuthorizationCode")
    val oauthAuthorizationCode: String? = null,
    @SerialName("oauthExpireIn")
    val oauthExpireIn: String? = null,
    @SerialName("profilePicture")
    val profilePicture: String? = null,
    @SerialName("refreshToken")
    val refreshToken: String? = null,
    @SerialName("registered")
    val registered: String? = null
)