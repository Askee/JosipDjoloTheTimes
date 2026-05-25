package com.josipdjolo.thetimes.feature.home

import com.josipdjolo.thetimes.core.domain.User
import com.josipdjolo.thetimes.core.ui.UiText

data class HomeViewStateData(
    val users: List<UserData>
)

data class UserData(
    val user: User,
    val isFollowing: Boolean
)

sealed class HomeViewState {
    data class Success(val data: HomeViewStateData) : HomeViewState()
    data class Error(val message: UiText) : HomeViewState()
    data object Loading : HomeViewState()
}
