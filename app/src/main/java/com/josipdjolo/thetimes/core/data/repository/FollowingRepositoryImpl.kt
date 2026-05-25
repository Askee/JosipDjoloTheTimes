package com.josipdjolo.thetimes.core.data.repository

import com.josipdjolo.thetimes.core.data.di.FollowingDataStoreLocalProvider
import com.josipdjolo.thetimes.core.data.local.provider.FollowingLocalProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowingRepositoryImpl @Inject constructor(
    /*
     Simply replace qualifier FollowingDSLocalProvider with FollowingRoomLocalProvider
     in order to switch out the provider.
     */
    @param:FollowingDataStoreLocalProvider val followingLocalProvider: FollowingLocalProvider
) : FollowingRepository {
    override val following: Flow<List<Long>>
        get() = followingLocalProvider.followedUsersFlow

    override suspend fun follow(userId: Long) {
        followingLocalProvider.follow(userId)
    }

    override suspend fun unfollow(userId: Long) {
        followingLocalProvider.unfollow(userId)
    }
}