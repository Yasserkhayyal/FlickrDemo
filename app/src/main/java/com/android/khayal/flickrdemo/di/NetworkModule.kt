package com.android.khayal.flickrdemo.di

import com.android.khayal.flickrdemo.api.FlickrSearchService
import com.android.khayal.flickrdemo.api.LoggingInterceptor
import com.android.khayal.flickrdemo.utils.LiveDataCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: LoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }


    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        liveDataCallAdapterFactory: LiveDataCallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .addCallAdapterFactory(liveDataCallAdapterFactory)
            .addConverterFactory(gsonConverterFactory).build()
    }


    @Singleton
    @Provides
    fun provideFlickrSearchRetrofitService(retrofit: Retrofit):FlickrSearchService{
        return retrofit.create(FlickrSearchService::class.java)
    }

}


@Module
@InstallIn(ApplicationComponent::class)
abstract class LiveDataCallAdapterModule {

    @Singleton
    @Binds
    abstract fun bindsLiveDataCallAdapterFactory(liveDataCallAdapterFactory: LiveDataCallAdapterFactory): CallAdapter.Factory
}