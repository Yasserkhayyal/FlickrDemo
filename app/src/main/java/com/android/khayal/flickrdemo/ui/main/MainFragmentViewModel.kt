package com.android.khayal.flickrdemo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.android.khayal.flickrdemo.repository.DataRepository
import com.android.khayal.flickrdemo.vo.Resource
import com.android.khayal.flickrdemo.vo.SearchResponse
import com.android.khayal.flickrdemo.vo.Status

class MainFragmentViewModel(val repository: DataRepository) : ViewModel(),
    Observer<Resource<Array<SearchResponse.Item>>> {

    val feed = MutableLiveData<Array<SearchResponse.Item>>()
    val error = MutableLiveData<String?>()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getSearchData(tags: String, tagMode: String) {
        repository.fetchData(keys = tags, tagMode = tagMode).observeForever(this)
    }

    override fun onCleared() {
        repository.removeObserver(this)
    }

    override fun onChanged(resultType: Resource<Array<SearchResponse.Item>>) {
        when (resultType.status) {
            Status.LOADING -> {
                showLoading.value = true
                feed.value = resultType.data
                error.value = resultType.message
            }
            Status.SUCCESS -> {
                showLoading.value = false
                feed.value = resultType.data
                error.value = resultType.message
                repository.removeObserver(this)
            }
            Status.ERROR -> {
                showLoading.value = false
                feed.value = resultType.data
                error.value = resultType.message
                repository.removeObserver(this)
            }
        }
    }
}
