package com.android.khayal.flickrdemo.di

import com.android.khayal.flickrdemo.AppExecutors
import com.android.khayal.flickrdemo.di.DiskIOExecutor
import com.android.khayal.flickrdemo.di.NetworkIOExecutor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
@InstallIn(ApplicationComponent::class)
object AppExecutorsModule {

    @DiskIOExecutor
    @Provides
    fun provideDiskIOExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @NetworkIOExecutor
    @Provides
    fun provideNetworkIOExecutor(): Executor {
        return Executors.newFixedThreadPool(3)
    }

    @Provides
    fun provideAppExecutors(
        @DiskIOExecutor diskIOExecutor: Executor,
        @NetworkIOExecutor networkExecutor: Executor,
        mainThreadExecutor: AppExecutors.MainThreadExecutor
    ): AppExecutors {
        return AppExecutors(diskIOExecutor,networkExecutor,mainThreadExecutor)
    }
}