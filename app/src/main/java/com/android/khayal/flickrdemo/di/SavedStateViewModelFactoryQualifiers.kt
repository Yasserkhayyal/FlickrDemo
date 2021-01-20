package com.android.khayal.flickrdemo.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SavedStateActivityViewModelFactory

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SavedStateFragmentViewModelFactory