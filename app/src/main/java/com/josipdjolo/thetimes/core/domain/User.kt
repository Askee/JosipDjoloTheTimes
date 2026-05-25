package com.josipdjolo.thetimes.core.domain

data class User(
    val userId: Long,
    val image: String?,
    val name: String,
    val reputation: Int
)