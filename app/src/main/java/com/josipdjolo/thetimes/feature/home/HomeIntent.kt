package com.josipdjolo.thetimes.feature.home

import com.josipdjolo.thetimes.core.domain.User

sealed class HomeIntent {
    data object ViewScreen : HomeIntent()
    data class Follow(val user: User) : HomeIntent()
    data class Unfollow(val user: User) : HomeIntent()
}
