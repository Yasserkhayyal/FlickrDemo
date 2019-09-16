package com.android.khayal.flickrdemo.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.khayal.flickrdemo.R
import com.android.khayal.flickrdemo.databinding.MainFragmentBinding
import com.android.khayal.flickrdemo.listeners.RecyclerItemClickListener
import com.android.khayal.flickrdemo.repository.DataRepository


class MainFragment : Fragment(), RecyclerItemClickListener.OnRecyclerClickListener
    , SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var databinding: MainFragmentBinding
    val viewModel by lazy {
        ViewModelProviders.of(
            this,
            MainFragmentViewModelFactory(DataRepository())
        )
            .get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        databinding = DataBindingUtil.inflate<MainFragmentBinding>(
            LayoutInflater.from(context),
            R.layout.main_fragment,
            container,
            false
        )
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databinding.viewModel = viewModel
        databinding.mainFragment = this
        databinding.lifecycleOwner = this
        val searchKey = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext).getString(
            getString(R.string.query_key), ""
        ) ?: ""
        if (searchKey.isNotEmpty()) {//to execute search at least once
            viewModel.getSearchData(tags = searchKey, tagMode = "Any")
        }
    }

    override fun onStart() {
        super.onStart()
        PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        //unregister OnSharedPreferenceChangeListener here in order to detect
        //the changes in the SearchActivity
        PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
            .unregisterOnSharedPreferenceChangeListener(this)
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
}
