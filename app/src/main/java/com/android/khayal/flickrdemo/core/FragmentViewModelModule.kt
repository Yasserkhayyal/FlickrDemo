package com.android.khayal.flickrdemo.core

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelAssistedFactory
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import com.android.khayal.flickrdemo.di.SavedStateFragmentViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Provider

@Module
@InstallIn(FragmentComponent::class)
object FragmentViewModelModule {

    @SavedStateFragmentViewModelFactory
    @Provides
    fun provideSavedStateViewModelFactory(
        application: Application,
        fragment: Fragment,
        viewModelFactories: @JvmSuppressWildcards Map<String, Provider<ViewModelAssistedFactory<out ViewModel>>>,
    ): DFMSavedStateViewModelFactory {
        val defaultArgs = fragment.arguments
        val delegate = SavedStateViewModelFactory(application, fragment, defaultArgs)
        return DFMSavedStateViewModelFactory(fragment, defaultArgs, delegate, viewModelFactories)
    }
}