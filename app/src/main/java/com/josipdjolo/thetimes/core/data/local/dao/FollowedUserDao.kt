package com.josipdjolo.thetimes.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.josipdjolo.thetimes.core.data.local.entity.FollowedUser
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowedUserDao {
    @Query("SELECT user_id FROM followed_user")
    fun observeAll(): Flow<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: FollowedUser)

    @Delete
    fun delete(user: FollowedUser)
}