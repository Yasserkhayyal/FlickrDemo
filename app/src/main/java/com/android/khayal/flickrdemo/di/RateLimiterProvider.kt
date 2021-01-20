package com.android.khayal.flickrdemo.di

import com.android.khayal.flickrdemo.utils.RateLimiter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RateLimiterProvider {

    @Singleton
    @Provides
    fun provideRateLimiter():RateLimiter<String>{
        return RateLimiter(10,TimeUnit.MINUTES)
    }
}