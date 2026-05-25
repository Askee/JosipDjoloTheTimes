package com.josipdjolo.thetimes.core.data.repository

import com.josipdjolo.thetimes.core.data.remote.request.UserRequest

interface StackOverflowRepository {
    suspend fun getUsers(userRequest: UserRequest): StackOverflowResult
}