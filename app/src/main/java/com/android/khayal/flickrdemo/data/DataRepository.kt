package com.android.khayal.flickrdemo.data

import com.android.khayal.flickrdemo.models.SearchResponse
import com.android.khayal.flickrdemo.network.FlickrSearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class DataRepository {
    var disposable: Disposable? = null
    val FlickrSearchServe by lazy {
        FlickrSearchService.create()
    }
    lateinit var successCallback: Consumer<SearchResponse.Content>
    lateinit var failureCallback: Consumer<Throwable>

    fun initCallbacks(
        successCallback: Consumer<SearchResponse.Content>,
        failureCallback: Consumer<Throwable>
    ) {
        this.successCallback = successCallback
        this.failureCallback = failureCallback
    }

    fun fetchData(keys: String, tagMode: String) {
        disposable = FlickrSearchServe.searchFor(keys, tagMode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {successCallback.accept(it)},
                {failureCallback.accept(it)}
            )
    }
}