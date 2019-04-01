package com.android.khayal.flickrdemo.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.android.khayal.flickrdemo.data.DataRepository
import com.android.khayal.flickrdemo.models.SearchResponse
import io.reactivex.ObservableSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainFragmentViewModel(
    private val repository: DataRepository = DataRepository(),
    private val disposables: CompositeDisposable = CompositeDisposable()
) : ViewModel() {

    val feed: MutableLiveData<Array<SearchResponse.Item>> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getSearchData(tags: String, tagMode: String) {
        val d = repository.fetchData(tags, tagMode)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading.postValue(true) }
            .doOnError { showLoading.postValue(false) }
            .doOnComplete { showLoading.postValue(false) }
            .doOnDispose { showLoading.postValue(false) }
            .subscribe(::onResponseSuccess, ::onResponseFail)
        disposables.add(d)
    }

    fun onResponseSuccess(response: SearchResponse.Content) {
        feed.postValue(response.items)
    }

    fun onResponseFail(error: Throwable) {
        feed.postValue(null)
    }

    override public fun onCleared() {
        disposables.dispose()
    }

}
