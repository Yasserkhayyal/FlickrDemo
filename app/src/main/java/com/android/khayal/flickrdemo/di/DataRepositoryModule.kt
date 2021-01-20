package com.android.khayal.flickrdemo.di

import com.android.khayal.flickrdemo.repository.DataRepositoryImp
import com.android.khayal.flickrdemo.repository.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
abstract class DataRepositoryModule {

    @Binds
    @FragmentScoped
    abstract fun provideDataRepository(dataRepository: DataRepositoryImp): DataRepository
}