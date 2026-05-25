package com.josipdjolo.thetimes.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.josipdjolo.thetimes.core.data.local.dao.FollowedUserDao
import com.josipdjolo.thetimes.core.data.local.provider.FollowingDataStoreLocalProviderImpl
import com.josipdjolo.thetimes.core.data.local.provider.FollowingLocalProvider
import com.josipdjolo.thetimes.core.data.local.provider.FollowingRoomLocalProviderImpl
import com.josipdjolo.thetimes.core.data.repository.FollowingRepository
import com.josipdjolo.thetimes.core.data.repository.FollowingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    private val Context.dataStore by preferencesDataStore(
        name = "josipdjolo_thetimes_app_preferences"
    )

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @FollowingDataStoreLocalProvider
    fun provideFollowingDataStoreLocalProviderImpl(
        dataStore: DataStore<Preferences>
    ): FollowingLocalProvider {
        return FollowingDataStoreLocalProviderImpl(dataStore)
    }

    @Provides
    @FollowingRoomLocalProvider
    fun provideFollowingRoomLocalProviderImpl(
        followedUserDao: FollowedUserDao
    ): FollowingLocalProvider {
        return FollowingRoomLocalProviderImpl(followedUserDao)
    }

    @Provides
    @Singleton
    fun provideFollowingRepository(
        followingRepositoryImpl: FollowingRepositoryImpl
    ): FollowingRepository {
        return followingRepositoryImpl
    }
}