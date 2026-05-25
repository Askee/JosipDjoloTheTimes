package com.josipdjolo.thetimes.core.data.local.provider

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FollowingDataStoreLocalProviderImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : FollowingLocalProvider {

    /*
    A naive approach to adding to a serialised list of Long values just as a fun example.
    This approach is prone to allowing for duplicates in theory, but left in as an example of a
    potential out-of-box approach to persisting data.
    A more robust approach would be by not skipping deserialisation, such as in the unfollow
    approach.
    Additionally, using Set instead of List would help with ensuring no issues regarding duplicates.
     */
    override suspend fun follow(userId: Long) {
        dataStore.edit { preferences ->
            val stringList = preferences[KEY_FOLLOWING_LIST] ?: ""
            preferences[KEY_FOLLOWING_LIST] =
                if (stringList.isEmpty()) "$userId" else stringList.plus(",$userId")
        }
    }

    override val followedUsersFlow: Flow<List<Long>>
        get() = dataStore.data.map { preferences ->
            val stringList = preferences[KEY_FOLLOWING_LIST] ?: ""
            if (stringList.isEmpty()) emptyList()
            else stringList.split(",").map { it.toLong() }
        }

    override suspend fun unfollow(userId: Long) {
        dataStore.edit { preferences ->
            val stringList = preferences[KEY_FOLLOWING_LIST] ?: ""
            preferences[KEY_FOLLOWING_LIST] =
                stringList.split(",").map { it.toLong() }.toMutableList().apply { remove(userId) }
                    .joinToString(",")

        }
    }

    companion object {
        val KEY_FOLLOWING_LIST = stringPreferencesKey("key_following_list")
    }
}