package com.josipdjolo.thetimes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josipdjolo.thetimes.core.dispatcher.IoDispatcher
import com.josipdjolo.thetimes.R
import com.josipdjolo.thetimes.core.ui.UiText
import com.josipdjolo.thetimes.feature.home.usecase.FollowingUseCase
import com.josipdjolo.thetimes.feature.home.usecase.GetTopUsersUseCase
import com.josipdjolo.thetimes.feature.home.usecase.TopUsersResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO Add unit tests
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopUsersUseCase: GetTopUsersUseCase,
    private val followingUseCase: FollowingUseCase,
    @param:IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.ViewScreen -> {
                viewModelScope.launch(coroutineDispatcher) { getData() }
            }

            is HomeIntent.Follow -> {
                viewModelScope.launch(coroutineDispatcher) {
                    followingUseCase.follow(intent.user.userId)
                }
            }

            is HomeIntent.Unfollow -> viewModelScope.launch(coroutineDispatcher) {
                followingUseCase.unfollow(intent.user.userId)
            }
        }
    }

    suspend fun getData() {
        combine(
            flowOf(getTopUsersUseCase.getTopUsers(TOP_USERS_LIMIT)),
            followingUseCase.following
        ) { result, followingIds ->
            _viewState.update {
                when (result) {
                    TopUsersResult.NetworkError -> HomeViewState.Error(UiText.Resource(R.string.network_error_message))
                    is TopUsersResult.NetworkSuccess -> HomeViewState.Success(
                        HomeViewStateData(
                            users = result.users.map {
                                UserData(
                                    user = it,
                                    isFollowing = followingIds.contains(it.userId)
                                )
                            }
                        )
                    )
                }
            }

        }.collect()
    }

    companion object {
        const val TOP_USERS_LIMIT = 20
    }

}