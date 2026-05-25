package com.josipdjolo.thetimes.core.data.remote

import com.josipdjolo.thetimes.core.data.remote.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.stackexchange.com/" //TODO Move to BuildConfig

interface StackOverflowApi {

    companion object {
        const val VERSION = "2.3"
        const val USERS_API = "$BASE_URL/$VERSION/users"
    }

    @GET(USERS_API)
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("site") site: String,
    ): UsersResponse
}