package com.josipdjolo.thetimes.core.data.local.provider

import kotlinx.coroutines.flow.Flow

interface FollowingLocalProvider {
    suspend fun follow(userId: Long)
    val followedUsersFlow: Flow<List<Long>>
    suspend fun unfollow(userId: Long)
}