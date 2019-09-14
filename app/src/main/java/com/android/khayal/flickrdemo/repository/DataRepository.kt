package com.android.khayal.flickrdemo.repository

import com.android.khayal.flickrdemo.vo.SearchResponse
import com.android.khayal.flickrdemo.api.FlickrSearchService
import io.reactivex.Observable
import io.reactivex.Single

class DataRepository {
    val flickrSearchServe by lazy { FlickrSearchService.create() }

    fun fetchData(keys: String, tagMode: String): Single<SearchResponse.Content> {
        return flickrSearchServe.searchFor(keys, tagMode)
    }
}