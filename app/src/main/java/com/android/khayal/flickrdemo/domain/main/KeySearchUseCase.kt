package com.android.khayal.flickrdemo.domain.main

import androidx.lifecycle.LiveData
import com.android.khayal.flickrdemo.vo.Resource
import com.android.khayal.flickrdemo.vo.SearchResponse

interface KeySearchUseCase {

    fun getResults(tags:String,tagMode:String): LiveData<Resource<Array<SearchResponse.Item>>>
}