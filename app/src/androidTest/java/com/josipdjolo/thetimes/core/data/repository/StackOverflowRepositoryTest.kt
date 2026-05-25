package com.josipdjolo.thetimes.core.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.josipdjolo.thetimes.core.data.remote.ApiResult
import com.josipdjolo.thetimes.core.data.remote.mappers.toRepositoryResult
import com.josipdjolo.thetimes.core.data.remote.model.UsersResponse
import com.josipdjolo.thetimes.core.data.remote.provider.StackOverflowRemoteProviderImpl
import com.josipdjolo.thetimes.core.data.remote.request.Order
import com.josipdjolo.thetimes.core.data.remote.request.Site
import com.josipdjolo.thetimes.core.data.remote.request.Sort
import com.josipdjolo.thetimes.core.data.remote.request.UserRequest
import com.josipdjolo.thetimes.helper.createMockUserItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StackOverflowRepositoryTest {

    @Test
    fun givenStackOverflowProviderGetUsersMethodReturnsApiResultSuccess_whenStackOverFlowRepositoryGetUsersMethodIsRequested_thenReturnRepositoryResultSuccess() =
        runTest {
            val mockUsers = List(20) { createMockUserItem(it) }
            val expectedResponse = ApiResult.Success(
                UsersResponse(
                    items = mockUsers,
                    hasMore = true,
                    quotaRemaining = 0,
                    quotaMax = 0
                )
            )

            val mockProvider = mockk<StackOverflowRemoteProviderImpl>()
            coEvery {
                mockProvider.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns expectedResponse

            val repository = StackOverflowRepositoryImpl(mockProvider)
            val actualResult = repository.getUsers(
                UserRequest(
                    sort = Sort.REPUTATION,
                    page = 1,
                    pageSize = 20,
                    order = Order.DESCENDING,
                    site = Site.STACKOVERFLOW
                )
            )

            Assert.assertEquals(expectedResponse.toRepositoryResult(), actualResult)
        }

    @Test
    fun givenStackOverflowProviderGetUsersMethodReturnsApiResultError_whenStackOverFlowRepositoryGetUsersMethodIsRequested_thenReturnRepositoryResultError() =
        runTest {
            val expectedResponse = ApiResult.Error()

            val mockProvider = mockk<StackOverflowRemoteProviderImpl>()

            coEvery {
                mockProvider.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns expectedResponse

            val repository = StackOverflowRepositoryImpl(mockProvider)

            val actualResult = repository.getUsers(
                UserRequest(
                    sort = Sort.REPUTATION,
                    page = 1,
                    pageSize = 20,
                    order = Order.DESCENDING,
                    site = Site.STACKOVERFLOW
                )
            )

            Assert.assertEquals(expectedResponse.toRepositoryResult(), actualResult)
        }
}