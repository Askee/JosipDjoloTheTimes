package com.josipdjolo.thetimes.core.data.local.provider

import com.josipdjolo.thetimes.core.data.local.dao.FollowedUserDao
import com.josipdjolo.thetimes.core.data.local.entity.FollowedUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowingRoomLocalProviderImpl @Inject constructor(
    private val followedUserDao: FollowedUserDao
) : FollowingLocalProvider {

    override suspend fun follow(userId: Long) {
        followedUserDao.insert(FollowedUser(userId))
    }

    override val followedUsersFlow: Flow<List<Long>>
        get() = followedUserDao.observeAll()

    override suspend fun unfollow(userId: Long) {
        followedUserDao.delete(FollowedUser(userId))
    }
}