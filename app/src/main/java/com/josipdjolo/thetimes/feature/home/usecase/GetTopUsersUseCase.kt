package com.josipdjolo.thetimes.feature.home.usecase

interface GetTopUsersUseCase {
    suspend fun getTopUsers(limit: Int): TopUsersResult
}