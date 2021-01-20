package com.android.khayal.flickrdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.khayal.flickrdemo.vo.SearchResponse
import javax.inject.Inject

@Database(
    entities = arrayOf(
        SearchResponse.Item::class,
        SearchResponse.Media::class
    ), version = 1
)
abstract class FlickrDemoDataBase : RoomDatabase() {

    abstract fun searchItemDao(): SearchItemDao

    companion object {
        @Volatile
        private var INSTANCE: FlickrDemoDataBase? = null

        fun getDataBase(context: Context): FlickrDemoDataBase {
            return INSTANCE ?: synchronized(this) {
                var instance = Room.databaseBuilder(
                    context, FlickrDemoDataBase::class.java,
                    "flickr_demo_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}