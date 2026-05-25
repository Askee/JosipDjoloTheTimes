package com.josipdjolo.thetimes.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.josipdjolo.thetimes.core.data.local.dao.FollowedUserDao
import com.josipdjolo.thetimes.core.data.local.entity.FollowedUser

@Database(entities = [FollowedUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun followedUserDao(): FollowedUserDao
}