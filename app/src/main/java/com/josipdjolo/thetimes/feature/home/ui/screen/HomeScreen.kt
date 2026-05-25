package com.josipdjolo.thetimes.feature.home.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.josipdjolo.thetimes.R
import com.josipdjolo.thetimes.core.domain.User
import com.josipdjolo.thetimes.core.ui.JosipPreview
import com.josipdjolo.thetimes.core.ui.ShimmerProvider
import com.josipdjolo.thetimes.core.ui.asString
import com.josipdjolo.thetimes.core.ui.theme.JosipTheme
import com.josipdjolo.thetimes.feature.home.HomeIntent
import com.josipdjolo.thetimes.feature.home.HomeViewModel
import com.josipdjolo.thetimes.feature.home.HomeViewState
import com.josipdjolo.thetimes.feature.home.UserData
import com.josipdjolo.thetimes.feature.home.ui.component.UserCard
import com.josipdjolo.thetimes.feature.home.ui.component.UserCardLoading

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(HomeIntent.ViewScreen)
    }

    ShimmerProvider {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.home_screen_title))

            when (viewState) {
                is HomeViewState.Error -> {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text((viewState as HomeViewState.Error).message.asString())
                    }
                }

                HomeViewState.Loading -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UserCardLoading()
                    }
                }

                is HomeViewState.Success -> {
                    UsersList(
                        users = (viewState as HomeViewState.Success).data.users,
                        onIntent = { intent ->
                            viewModel.onIntent(intent)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun UsersList(users: List<UserData>, onIntent: (HomeIntent) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(users) { userData ->
            UserCard(
                userData,
                onFollow = { onIntent(HomeIntent.Follow(userData.user)) },
                onUnfollow = { onIntent(HomeIntent.Unfollow(userData.user)) }
            )
        }
    }

}


@JosipPreview
@Composable
fun UsersListPreview() {
    JosipTheme {
        UsersList(
            users = List(10) {
                UserData(
                    user = User(
                        1,
                        "",
                        name = "Name",
                        reputation = 123
                    ),
                    isFollowing = it % 2 == 0
                )
            },
            onIntent = {}
        )
    }
}