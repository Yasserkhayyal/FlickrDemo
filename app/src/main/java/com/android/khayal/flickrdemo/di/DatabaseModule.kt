package com.android.khayal.flickrdemo.di

import android.content.Context
import androidx.room.RoomDatabase
import com.android.khayal.flickrdemo.db.FlickrDemoDataBase
import com.android.khayal.flickrdemo.db.SearchItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FlickrDemoDataBase {
        return FlickrDemoDataBase.getDataBase(context)
    }

    @Singleton
    @Provides
    fun provideSearchItemDao(flickrDemoDataBase: FlickrDemoDataBase):SearchItemDao{
        return flickrDemoDataBase.searchItemDao()
    }
}