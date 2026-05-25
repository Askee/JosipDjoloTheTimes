package com.josipdjolo.thetimes.core.data.remote.provider

import com.josipdjolo.thetimes.core.data.remote.ApiResult
import com.josipdjolo.thetimes.core.data.remote.model.UsersResponse

interface StackOverflowRemoteProvider {
    suspend fun getUsers(
        page: Int,
        pageSize: Int,
        sort: String, order: String,
        site: String,
    ): ApiResult<UsersResponse>
}