package com.josipdjolo.thetimes.core.data.local.provider

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class FollowingDataStoreLocalProviderImplTest {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var provider: FollowingDataStoreLocalProviderImpl

    @Before
    fun setup() {
        dataStore = PreferenceDataStoreFactory.create(
            scope = TestScope(UnconfinedTestDispatcher()),
            produceFile = {
                File.createTempFile("followed_users_datastore", ".preferences_pb")
            }
        )

        provider = FollowingDataStoreLocalProviderImpl(dataStore)
    }

    @Test
    fun givenFollowedUsersListNotEmpty_whenObservingFollowedUsersFlow_thenReturnInitialList() =
        runTest {
            provider.follow(1L)

            val result = provider.followedUsersFlow.first()

            assertEquals(listOf(1L), result)
        }

    @Test
    fun givenFollowedUsersListNotEmpty_whenFollowingNewUser_thenReturnListWithNewUser() = runTest {
        provider.follow(1L)
        provider.follow(2L)

        provider.follow(3L)

        val result = provider.followedUsersFlow.first()

        assertEquals(listOf(1L, 2L, 3L), result)
    }

    @Test
    fun givenFollowedUsersListNotEmpty_whenUnfollowingUserFromList_thenReturnListWithoutUnfollowedUser() =
        runTest {
            provider.follow(1L)
            provider.follow(2L)
            provider.follow(3L)

            provider.unfollow(2L)

            val result = provider.followedUsersFlow.first()

            assertEquals(listOf(1L, 3L), result)
        }

    @Test
    fun givenFollowedUsersListNotEmpty_whenUnfollowingUserNotInTheList_thenReturnInitialList() =
        runTest {
            provider.follow(1L)
            provider.follow(2L)

            provider.unfollow(99L)

            val result = provider.followedUsersFlow.first()

            assertEquals(listOf(1L, 2L), result)
        }

    @Test
    fun givenNoFollowedUsers_whenObservingFollowedUsersFlow_thenReturnEmptyList() = runTest {
        val result = provider.followedUsersFlow.first()

        assertEquals(emptyList<Long>(), result)
    }
}