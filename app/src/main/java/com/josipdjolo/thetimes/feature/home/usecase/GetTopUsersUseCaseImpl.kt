package com.josipdjolo.thetimes.feature.home.usecase

import com.josipdjolo.thetimes.core.data.remote.mappers.toTopUsersResult
import com.josipdjolo.thetimes.core.data.remote.request.Order
import com.josipdjolo.thetimes.core.data.remote.request.Site
import com.josipdjolo.thetimes.core.data.remote.request.Sort
import com.josipdjolo.thetimes.core.data.remote.request.UserRequest
import com.josipdjolo.thetimes.core.data.repository.StackOverflowRepository
import com.josipdjolo.thetimes.core.domain.User
import javax.inject.Inject

class GetTopUsersUseCaseImpl @Inject constructor(
    private val stackOverflowRepository: StackOverflowRepository
) : GetTopUsersUseCase {
    override suspend fun getTopUsers(limit: Int): TopUsersResult {
        return stackOverflowRepository.getUsers(
            UserRequest(
                sort = Sort.REPUTATION,
                page = 1,
                pageSize = limit,
                order = Order.DESCENDING,
                site = Site.STACKOVERFLOW
            )
        ).toTopUsersResult()
    }
}


sealed class TopUsersResult {
    data class NetworkSuccess(val users: List<User>) : TopUsersResult()
    data object NetworkError : TopUsersResult()
}