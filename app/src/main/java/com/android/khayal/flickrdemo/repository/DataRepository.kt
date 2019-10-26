package com.android.khayal.flickrdemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.android.khayal.flickrdemo.AppExecutors
import com.android.khayal.flickrdemo.api.ApiResponse
import com.android.khayal.flickrdemo.api.ApiSuccessResponse
import com.android.khayal.flickrdemo.api.FlickrSearchService
import com.android.khayal.flickrdemo.db.FlickrDemoDataBase
import com.android.khayal.flickrdemo.db.SearchItemDao
import com.android.khayal.flickrdemo.utils.RateLimiter
import com.android.khayal.flickrdemo.vo.Resource
import com.android.khayal.flickrdemo.vo.SearchResponse
import java.util.concurrent.TimeUnit

class DataRepository(
    val appExecutors: AppExecutors,
    val db: FlickrDemoDataBase,
    val searchItemDao: SearchItemDao,
    val flickrSearchService: FlickrSearchService
) {

    private val searchResultsRateLimiter = RateLimiter<String>(10, TimeUnit.MINUTES)
    lateinit var networkBoundResourceResultLiveData: LiveData<Resource<Array<SearchResponse.Item>>>

    fun fetchData(keys: String, tagMode: String): LiveData<Resource<Array<SearchResponse.Item>>> {
        networkBoundResourceResultLiveData = object :
            NetworkBoundResource<Array<SearchResponse.Item>, SearchResponse.Content>(appExecutors) {
            override fun onFetchFailed() {
                searchResultsRateLimiter.reset(keys)
            }

            override fun processResponse(response: ApiSuccessResponse<SearchResponse.Content>): SearchResponse.Content {
                response.body.items.forEach {
                    it.tagMode = tagMode // as the tagMode is part of the request and not of result and will be used later in querying db
                }
                return response.body
            }

            override fun saveCallResult(apiResponse: SearchResponse.Content) {
                searchItemDao.insertSearchItems(apiResponse.items)
            }

            override fun shouldFetch(data: Array<SearchResponse.Item>?): Boolean {
                return data == null || data.isEmpty() || searchResultsRateLimiter.shouldFetch(keys)
            }

            override fun loadFromDb(): LiveData<Array<SearchResponse.Item>> {
                return searchItemDao.getSearchesByTags("%$keys%", tagMode)
            }

            override fun createCall(): LiveData<ApiResponse<SearchResponse.Content>> =
                flickrSearchService.searchFor(keys, tagMode)

        }.asLiveData()
        return networkBoundResourceResultLiveData
    }

    fun removeObserver(observer: Observer<Resource<Array<SearchResponse.Item>>>) {
        networkBoundResourceResultLiveData.removeObserver(observer)
    }
}