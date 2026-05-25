package com.josipdjolo.thetimes.core.data.remote.request

data class UserRequest(
    val sort: Sort,
    val page: Int,
    val pageSize: Int,
    val order: Order,
    val site: Site,
)

enum class Sort {
    REPUTATION,
    CREATION,
    NAME,
    MODIFIED,
}

enum class Order {
    ASCENDING, DESCENDING
}

enum class Site {
    STACKOVERFLOW
}