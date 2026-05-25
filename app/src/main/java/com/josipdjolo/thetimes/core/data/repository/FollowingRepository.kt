package com.josipdjolo.thetimes.core.data.repository

import kotlinx.coroutines.flow.Flow

interface FollowingRepository {
    val following: Flow<List<Long>>
    suspend fun follow(userId: Long)
    suspend fun unfollow(userId: Long)
}