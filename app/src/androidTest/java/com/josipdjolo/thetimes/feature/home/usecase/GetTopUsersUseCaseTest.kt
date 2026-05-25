package com.josipdjolo.thetimes.feature.home.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.josipdjolo.thetimes.core.data.remote.mappers.toTopUsersResult
import com.josipdjolo.thetimes.core.data.repository.StackOverflowRepository
import com.josipdjolo.thetimes.core.data.repository.StackOverflowResult
import com.josipdjolo.thetimes.helper.createMockUser
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetTopUsersUseCaseTest {

    @Test
    fun givenStackOverflowRepositoryGetUsersMethodReturnsRepositoryResultSuccess_whenGetTopUsersUseCaseIsInvoked_thenReturnTopUsersResultSuccess() =
        runTest {
            val mockUsers = List(20) { createMockUser(it) }
            val expectedResponse = StackOverflowResult.NetworkSuccess(mockUsers)

            val mockRepository = mockk<StackOverflowRepository>()

            coEvery { mockRepository.getUsers(any()) } returns expectedResponse

            val useCase = GetTopUsersUseCaseImpl(mockRepository)
            val actualResult = useCase.getTopUsers(limit = 20)

            Assert.assertEquals(expectedResponse.toTopUsersResult(), actualResult)
        }

    @Test
    fun givenStackOverflowRepositoryGetUsersMethodReturnsRepositoryResultError_whenGetTopUsersUseCaseIsInvoked_thenReturnTopUsersResultError() =
        runTest {
            val expectedResponse = StackOverflowResult.NetworkError

            val mockRepository = mockk<StackOverflowRepository>()

            coEvery { mockRepository.getUsers(any()) } returns expectedResponse

            val useCase = GetTopUsersUseCaseImpl(mockRepository)
            val actualResult = useCase.getTopUsers(limit = 20)

            Assert.assertEquals(expectedResponse.toTopUsersResult(), actualResult)
        }
}