package com.android.khayal.flickrdemo.di.main

import com.android.khayal.flickrdemo.domain.main.KeySearchUseCase
import com.android.khayal.flickrdemo.domain.main.KeySearchUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
abstract class KeySearchModule {

    @Binds
    @FragmentScoped
    abstract fun bindsKeySearchUseCase(keySearchUseCaseImp: KeySearchUseCaseImp): KeySearchUseCase
}