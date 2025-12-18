package auth.data.data_source.credential.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LookupResponse(
    @SerialName("kind")
    val kind: String,
    @SerialName("users")
    val users: List<User>
)


@Serializable
data class ProviderUserInfo(
    @SerialName("email")
    val email: String,
    @SerialName("federatedId")
    val federatedId: String,
    @SerialName("providerId")
    val providerId: String,
    @SerialName("rawId")
    val rawId: String
)


@Serializable
data class User(
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("email")
    val email: String,
    @SerialName("emailVerified")
    val emailVerified: Boolean,
    @SerialName("lastLoginAt")
    val lastLoginAt: String,
    @SerialName("lastRefreshAt")
    val lastRefreshAt: String,
    @SerialName("localId")
    val localId: String,
    @SerialName("passwordHash")
    val passwordHash: String,
    @SerialName("passwordUpdatedAt")
    val passwordUpdatedAt: Long,
    @SerialName("providerUserInfo")
    val providerUserInfo: List<ProviderUserInfo>,
    @SerialName("validSince")
    val validSince: String
)