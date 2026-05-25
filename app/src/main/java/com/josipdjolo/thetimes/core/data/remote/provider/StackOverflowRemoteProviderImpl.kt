package com.josipdjolo.thetimes.core.data.remote.provider

import com.josipdjolo.thetimes.core.data.remote.ApiResult
import com.josipdjolo.thetimes.core.data.remote.StackOverflowApi
import com.josipdjolo.thetimes.core.data.remote.model.UsersResponse
import javax.inject.Inject

class StackOverflowRemoteProviderImpl @Inject constructor(
    private val stackOverflowApi: StackOverflowApi
) : StackOverflowRemoteProvider {
    override suspend fun getUsers(
        page: Int,
        pageSize: Int,
        sort: String,
        order: String,
        site: String
    ): ApiResult<UsersResponse> {
        return try {
            val response = stackOverflowApi.getUsers(
                page = page,
                pageSize = pageSize,
                sort = sort,
                order = order,
                site = site
            )
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(exception = e)
        }
    }
}