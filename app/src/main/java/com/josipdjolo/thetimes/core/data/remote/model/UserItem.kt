package com.josipdjolo.thetimes.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserItem(
    @SerialName("reputation")
    val reputation: Int,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("profile_image")
    val profileImage: String?,
    @SerialName("display_name")
    val displayName: String
)