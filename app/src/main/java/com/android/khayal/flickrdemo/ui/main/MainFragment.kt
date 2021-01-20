package com.android.khayal.flickrdemo.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android.khayal.flickrdemo.R
import com.android.khayal.flickrdemo.core.DFMSavedStateViewModelFactory
import com.android.khayal.flickrdemo.databinding.MainFragmentBinding
import com.android.khayal.flickrdemo.di.SavedStateFragmentViewModelFactory
import com.android.khayal.flickrdemo.listeners.RecyclerItemClickListener
import com.android.khayal.flickrdemo.vo.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(),
    RecyclerItemClickListener.OnRecyclerClickListener
    , SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private var mainFragmentBinding: MainFragmentBinding? = null

    //valid to use only between the onCreateView() and onDestroyView()
    private val _mainFragmentBinding: MainFragmentBinding
        get() = mainFragmentBinding!!

    @SavedStateFragmentViewModelFactory
    @Inject lateinit var savedStateViewModelFactory: DFMSavedStateViewModelFactory
    private val viewModel by viewModels<MainFragmentViewModel>{savedStateViewModelFactory}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        mainFragmentBinding = MainFragmentBinding.inflate(layoutInflater)
        return _mainFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _mainFragmentBinding.viewModel = viewModel
        _mainFragmentBinding.mainFragment = this
        _mainFragmentBinding.lifecycleOwner = this
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(
                    _mainFragmentBinding.snackbarLayout,
                    String.format(getString(R.string.error_search_result), it),
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        })
        val searchKey =
            sharedPreferences.getString(
                getString(R.string.query_key), ""
            ) ?: ""
        if (searchKey.isNotEmpty()) {//to execute search at least once
            viewModel.getSearchData(tags = searchKey, tagMode = "Any").observe(viewLifecycleOwner,
                Observer { resultType ->
                    when (resultType.status) {
                        Status.LOADING -> {
                            viewModel.showLoading.value = true
                            viewModel.feed.value = resultType.data
                            viewModel.error.value = resultType.message
                        }
                        Status.SUCCESS -> {
                            viewModel.showLoading.value = false
                            viewModel.feed.value = resultType.data
                            viewModel.error.value = resultType.message
                        }
                        Status.ERROR -> {
                            viewModel.showLoading.value = false
                            viewModel.feed.value = resultType.data
                            viewModel.error.value = resultType.message
                        }
                    }
                })
        }
    }

    override fun onStart() {
        super.onStart()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        //unregister OnSharedPreferenceChangeListener here in order to detect
        //the changes in the SearchActivity
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(activity, "normal tap at $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Toast.makeText(activity, "long tap at $position", Toast.LENGTH_SHORT).show()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == getString(R.string.query_key)) {
            val searchKey = sharedPreferences.getString(getString(R.string.query_key), "") ?: ""
            if (searchKey.isNotEmpty() && isVisible && !isRemoving) {
                viewModel.getSearchData(tags = searchKey, tagMode = "Any")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainFragmentBinding = null
    }
}
