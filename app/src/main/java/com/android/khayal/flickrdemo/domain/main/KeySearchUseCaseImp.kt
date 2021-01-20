package com.android.khayal.flickrdemo.domain.main

import androidx.lifecycle.LiveData
import com.android.khayal.flickrdemo.domain.main.KeySearchUseCase
import com.android.khayal.flickrdemo.repository.DataRepository
import com.android.khayal.flickrdemo.vo.Resource
import com.android.khayal.flickrdemo.vo.SearchResponse
import javax.inject.Inject

class KeySearchUseCaseImp @Inject constructor(val repository: DataRepository):
    KeySearchUseCase {

    override fun getResults(tags: String, tagMode: String): LiveData<Resource<Array<SearchResponse.Item>>> {
        return repository.fetchData(tags,tagMode)
    }
}