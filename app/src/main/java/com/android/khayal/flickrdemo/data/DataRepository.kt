package com.android.khayal.flickrdemo.data

import com.android.khayal.flickrdemo.models.SearchResponse
import com.android.khayal.flickrdemo.network.FlickrSearchService
import io.reactivex.Observable

class DataRepository {
    val flickrSearchServe by lazy { FlickrSearchService.create() }

    fun fetchData(keys: String, tagMode: String): Observable<SearchResponse.Content> {
        return flickrSearchServe.searchFor(keys, tagMode)
    }
}