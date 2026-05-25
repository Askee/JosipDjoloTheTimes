package com.josipdjolo.thetimes.core.data.remote.mappers

import com.josipdjolo.thetimes.core.data.remote.ApiResult
import com.josipdjolo.thetimes.core.data.remote.model.UsersResponse
import com.josipdjolo.thetimes.core.data.remote.request.Order
import com.josipdjolo.thetimes.core.data.remote.request.Site
import com.josipdjolo.thetimes.core.data.remote.request.Sort
import com.josipdjolo.thetimes.core.data.repository.StackOverflowResult
import com.josipdjolo.thetimes.core.domain.User
import com.josipdjolo.thetimes.feature.home.usecase.TopUsersResult

//TODO Move to separate mapper files
//TODO Add unit tests
fun UsersResponse.toDomain(): List<User> {
    return this.items.map {
        User(
            userId = it.userId,
            image = it.profileImage,
            name = it.displayName,
            reputation = it.reputation
        )
    }
}

fun Sort.toValue() = when (this) {
    Sort.REPUTATION -> "reputation"
    Sort.CREATION -> "creation"
    Sort.NAME -> "name"
    Sort.MODIFIED -> "modified"
}

fun Order.toValue() = when (this) {
    Order.ASCENDING -> "asc"
    Order.DESCENDING -> "desc"
}

fun Site.toValue() = when (this) {
    Site.STACKOVERFLOW -> "stackoverflow"
}


fun ApiResult<UsersResponse>.toRepositoryResult(): StackOverflowResult {
    return when (this) {
        is ApiResult.Error -> StackOverflowResult.NetworkError
        is ApiResult.Success<UsersResponse> -> StackOverflowResult.NetworkSuccess(users = this.data.toDomain())
    }
}

fun StackOverflowResult.toTopUsersResult(): TopUsersResult {
    return when (this) {
        is StackOverflowResult.NetworkError -> TopUsersResult.NetworkError
        is StackOverflowResult.NetworkSuccess -> TopUsersResult.NetworkSuccess(users = this.users)
    }
}