package com.android.khayal.flickrdemo.core

import android.app.Activity
import android.app.Application
import androidx.hilt.lifecycle.ViewModelAssistedFactory
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.android.khayal.flickrdemo.di.SavedStateActivityViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Provider

@Module
@InstallIn(ActivityComponent::class)
object ActivityViewModelModule {

    @SavedStateActivityViewModelFactory
    @Provides
    fun provideSavedStateViewModelFactory(
        application: Application,
        activity: Activity,
        viewModelFactories: @JvmSuppressWildcards Map<String, Provider<ViewModelAssistedFactory<out ViewModel>>>,
    ): DFMSavedStateViewModelFactory {
        val owner = activity as SavedStateRegistryOwner
        val defaultArgs = activity.intent?.extras
        val delegate = SavedStateViewModelFactory(application, owner, defaultArgs)
        return DFMSavedStateViewModelFactory(owner, defaultArgs, delegate, viewModelFactories)
    }
}