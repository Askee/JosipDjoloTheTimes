package com.josipdjolo.thetimes.core.data.repository

import com.josipdjolo.thetimes.core.data.remote.mappers.toRepositoryResult
import com.josipdjolo.thetimes.core.data.remote.mappers.toValue
import com.josipdjolo.thetimes.core.data.remote.provider.StackOverflowRemoteProvider
import com.josipdjolo.thetimes.core.data.remote.request.UserRequest
import com.josipdjolo.thetimes.core.domain.User
import javax.inject.Inject

class StackOverflowRepositoryImpl @Inject constructor(
    private val stackOverflowRemoteProvider: StackOverflowRemoteProvider
) : StackOverflowRepository {
    override suspend fun getUsers(userRequest: UserRequest): StackOverflowResult {
        return stackOverflowRemoteProvider.getUsers(
            page = userRequest.page,
            pageSize = userRequest.pageSize,
            sort = userRequest.sort.toValue(),
            order = userRequest.order.toValue(),
            site = userRequest.site.toValue()
        ).toRepositoryResult()
    }
}

sealed class StackOverflowResult {
    data class NetworkSuccess(val users: List<User>) : StackOverflowResult()
    data object NetworkError : StackOverflowResult()
}