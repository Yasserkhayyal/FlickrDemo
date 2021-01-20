package com.android.khayal.flickrdemo.repository

import androidx.lifecycle.LiveData
import com.android.khayal.flickrdemo.vo.Resource
import com.android.khayal.flickrdemo.vo.SearchResponse

interface DataRepository {

    fun fetchData(keys: String, tagMode: String): LiveData<Resource<Array<SearchResponse.Item>>>

}