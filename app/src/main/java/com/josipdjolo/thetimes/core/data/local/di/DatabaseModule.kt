package com.josipdjolo.thetimes.core.data.local.di

import android.content.Context
import androidx.room.Room
import com.josipdjolo.thetimes.core.data.local.AppDatabase
import com.josipdjolo.thetimes.core.data.local.dao.FollowedUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "josipdjolo_thetimes"
        ).build()
    }

    @Provides
    fun provideFollowedUserDao(db: AppDatabase): FollowedUserDao {
        return db.followedUserDao()
    }
}