package com.josipdjolo.thetimes.helper

import com.josipdjolo.thetimes.core.data.remote.model.UserItem
import com.josipdjolo.thetimes.core.domain.User

fun createMockUserItem(
    id: Int,
) = UserItem(
    userId = id.toLong(),
    profileImage = "",
    displayName = "User $id",
    reputation = 0
)

fun createMockUser(
    id: Int,
) = User(
    userId = id.toLong(),
    image = "",
    name = "User $id",
    reputation = 0
)