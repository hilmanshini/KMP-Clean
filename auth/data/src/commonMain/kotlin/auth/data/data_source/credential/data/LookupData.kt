package auth.data.data_source.credential.data

import kotlinx.serialization.Serializable

@Serializable
data class LookupData(
    val idToken: String
)