package com.josipdjolo.thetimes.feature.home.usecase

import com.josipdjolo.thetimes.core.data.repository.FollowingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowingUseCaseImpl @Inject constructor(
    private val followingRepository: FollowingRepository
) : FollowingUseCase {
    override val following: Flow<List<Long>>
        get() = followingRepository.following

    override suspend fun follow(userId: Long) {
        followingRepository.follow(userId)
    }

    override suspend fun unfollow(userId: Long) {
        followingRepository.unfollow(userId)
    }
}