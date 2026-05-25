package com.josipdjolo.thetimes.core.data.remote

sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception? = null, val message: String? = null) :
        ApiResult<Nothing>()
}