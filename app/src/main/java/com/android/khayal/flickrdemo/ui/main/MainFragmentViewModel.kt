package com.android.khayal.flickrdemo.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.android.khayal.flickrdemo.domain.main.KeySearchUseCase
import com.android.khayal.flickrdemo.domain.main.KeySearchUseCaseImp
import com.android.khayal.flickrdemo.vo.Resource
import com.android.khayal.flickrdemo.vo.SearchResponse
import com.android.khayal.flickrdemo.vo.Status

class MainFragmentViewModel @ViewModelInject constructor(
    private val keySearchUseCase: KeySearchUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val feed = MutableLiveData<Array<SearchResponse.Item>>()
    val error = MutableLiveData<String?>()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getSearchData(
        tags: String,
        tagMode: String
    ): LiveData<Resource<Array<SearchResponse.Item>>> {
        return keySearchUseCase.getResults(tags = tags, tagMode = tagMode)
    }
}
