package com.android.khayal.flickrdemo.api

import androidx.lifecycle.LiveData
import com.android.khayal.flickrdemo.BuildConfig
import com.android.khayal.flickrdemo.utils.LiveDataCallAdapterFactory
import com.android.khayal.flickrdemo.vo.SearchResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrSearchService {
    @GET("https://api.flickr.com/services/feeds/photos_public.gne/")
    fun searchFor(
        @Query("tags") tags: String, @Query("tagmode") tagMode: String,
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Boolean = true
    ): LiveData<ApiResponse<SearchResponse.Content>>

    companion object {

        fun create(): FlickrSearchService {

            val okHttpClient = OkHttpClient.Builder().addInterceptor(LoggingInterceptor()).build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .client(okHttpClient)
                .baseUrl("https://api.flickr.com/services/feeds/photos_public.gne/")
                .build()

            return retrofit.create(FlickrSearchService::class.java)
        }
    }
}
