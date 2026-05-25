package com.josipdjolo.thetimes.core.data.remote.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.josipdjolo.thetimes.BuildConfig
import com.josipdjolo.thetimes.core.data.remote.BASE_URL
import com.josipdjolo.thetimes.core.data.remote.StackOverflowApi
import com.josipdjolo.thetimes.core.data.remote.provider.StackOverflowRemoteProvider
import com.josipdjolo.thetimes.core.data.remote.provider.StackOverflowRemoteProviderImpl
import com.josipdjolo.thetimes.core.data.repository.StackOverflowRepository
import com.josipdjolo.thetimes.core.data.repository.StackOverflowRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideUnauthorizedOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideStackOverflowApiService(okHttpClient: OkHttpClient): StackOverflowApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .build()
            .create(StackOverflowApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStackOverflowProvider(stackOverflowNetworkProviderImpl: StackOverflowRemoteProviderImpl): StackOverflowRemoteProvider {
        return stackOverflowNetworkProviderImpl
    }

    @Provides
    @Singleton
    fun provideStackOverflowRepository(stackOverflowRepositoryImpl: StackOverflowRepositoryImpl): StackOverflowRepository {
        return stackOverflowRepositoryImpl
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnauthorizedClient