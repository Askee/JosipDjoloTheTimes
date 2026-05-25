package com.josipdjolo.thetimes.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    @SerialName("items")
    val items: List<UserItem>,
    @SerialName("has_more")
    val hasMore: Boolean,
    @SerialName("quota_max")
    val quotaMax: Int,
    @SerialName("quota_remaining")
    val quotaRemaining: Int
)