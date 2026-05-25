package com.josipdjolo.thetimes.core.data.remote.provider

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.josipdjolo.thetimes.core.data.remote.ApiResult
import com.josipdjolo.thetimes.core.data.remote.StackOverflowApi
import com.josipdjolo.thetimes.core.data.remote.model.UsersResponse
import com.josipdjolo.thetimes.helper.createMockUserItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StackOverflowRemoteProviderTest {

    @Test
    fun givenStackOverflowApiGetUsersMethodReturnsUsersResponse_whenStackOverflowProviderGetUsersMethodIsRequested_thenReturnApiResultSuccess() =
        runTest {
            val mockApiService = mockk<StackOverflowApi>()
            val stackOverflowRemoteProviderImpl = StackOverflowRemoteProviderImpl(mockApiService)

            val mockUsers = List(20) { createMockUserItem(it) }

            val expectedResponse = UsersResponse(
                items = mockUsers,
                hasMore = true,
                quotaRemaining = 0,
                quotaMax = 0
            )

            coEvery {
                mockApiService.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns expectedResponse

            val actualResult = stackOverflowRemoteProviderImpl.getUsers(
                sort = "",
                page = 1,
                pageSize = 20,
                order = "",
                site = ""
            )

            val expectedResult = ApiResult.Success(expectedResponse)

            Assert.assertEquals(expectedResult, actualResult)
        }

    @Test
    fun givenStackOverflowApiGetUsersMethodThrowsException_whenStackOverflowProviderGetUsersMethodIsRequested_thenReturnApiResultError() =
        runTest {
            val mockApiService = mockk<StackOverflowApi>()
            val stackOverflowRemoteProviderImpl = StackOverflowRemoteProviderImpl(mockApiService)

            val expectedException = NullPointerException("Test exception")

            coEvery {
                mockApiService.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } throws expectedException

            val actualResult = stackOverflowRemoteProviderImpl.getUsers(
                sort = "",
                page = 1,
                pageSize = 20,
                order = "",
                site = ""
            )

            val expectedResult = ApiResult.Error(expectedException)

            Assert.assertEquals(expectedResult, actualResult)
        }
}