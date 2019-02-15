package com.android.khayal.flickrdemo.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.android.khayal.flickrdemo.data.DataRepository
import com.android.khayal.flickrdemo.models.SearchResponse
import io.reactivex.functions.Consumer

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var repository: DataRepository
    val stringBuilder: StringBuilder = StringBuilder()
    val feed = MutableLiveData<Array<SearchResponse.Item>>()

    fun GetSearchData(tags: String, tagMode: String){
        repository = DataRepository()
        repository.initCallbacks(Consumer { onResponseSuccess(it) },
            Consumer { onResponseFail(it) })

        repository.fetchData(tags, tagMode)
    }

    fun onResponseSuccess(response: SearchResponse.Content) {
        feed.postValue(response.items)
    }

    fun onResponseFail(error: Throwable) {
        feed.postValue(null)
    }

    override fun onCleared() {
        repository.disposable?.dispose()
    }

}
