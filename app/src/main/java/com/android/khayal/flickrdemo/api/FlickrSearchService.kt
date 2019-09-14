package com.android.khayal.flickrdemo.api

import com.android.khayal.flickrdemo.BuildConfig
import com.android.khayal.flickrdemo.vo.SearchResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
    ): Single<SearchResponse.Content>

    companion object {

        fun create(): FlickrSearchService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("https://api.flickr.com/services/feeds/photos_public.gne/")
                .build()

            return retrofit.create(FlickrSearchService::class.java)
        }
    }
}
