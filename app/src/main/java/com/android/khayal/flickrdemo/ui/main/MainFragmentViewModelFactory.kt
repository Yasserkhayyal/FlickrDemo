package com.android.khayal.flickrdemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.khayal.flickrdemo.db.SearchItemDao
import com.android.khayal.flickrdemo.repository.DataRepository

class MainFragmentViewModelFactory(val repository: DataRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(repository) as T
    }
}