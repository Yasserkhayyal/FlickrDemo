package com.android.khayal.flickrdemo.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DiskIOExecutor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkIOExecutor