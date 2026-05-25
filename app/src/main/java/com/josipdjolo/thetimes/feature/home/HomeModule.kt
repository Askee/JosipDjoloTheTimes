package com.josipdjolo.thetimes.feature.home

import com.josipdjolo.thetimes.feature.home.usecase.FollowingUseCase
import com.josipdjolo.thetimes.feature.home.usecase.FollowingUseCaseImpl
import com.josipdjolo.thetimes.feature.home.usecase.GetTopUsersUseCase
import com.josipdjolo.thetimes.feature.home.usecase.GetTopUsersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {
    @Provides
    fun provideFollowingUseCase(followingUseCaseImpl: FollowingUseCaseImpl): FollowingUseCase {
        return followingUseCaseImpl
    }

    @Provides
    fun provideGetTopUsersUseCase(getTopUsersUseCaseImpl: GetTopUsersUseCaseImpl): GetTopUsersUseCase {
        return getTopUsersUseCaseImpl
    }
}