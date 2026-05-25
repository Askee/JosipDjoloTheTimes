package com.josipdjolo.thetimes.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BadgeCounts(
    @SerialName("bronze")
    val bronze: Int,
    @SerialName("silver")
    val silver: Int,
    @SerialName("gold")
    val gold: Int
)