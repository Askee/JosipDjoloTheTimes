package com.josipdjolo.thetimes.feature.home.usecase

import kotlinx.coroutines.flow.Flow

interface FollowingUseCase {
    val following: Flow<List<Long>>

    suspend fun follow(userId: Long)
    suspend fun unfollow(userId: Long)
}