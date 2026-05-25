package com.josipdjolo.thetimes.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followed_user")
data class FollowedUser(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userId: Long
)