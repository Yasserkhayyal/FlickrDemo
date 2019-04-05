package com.android.khayal.flickrdemo.main

import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import com.android.khayal.flickrdemo.R
import com.android.khayal.flickrdemo.data.DataRepository
import com.android.khayal.flickrdemo.databinding.MainFragmentBinding
import com.android.khayal.flickrdemo.listeners.NewIntentListener
import com.android.khayal.flickrdemo.listeners.RecyclerItemClickListener


class MainFragment : Fragment(), RecyclerItemClickListener.OnRecyclerClickListener
    , SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    val viewModel by lazy {
        ViewModelProviders.of(this, MainFragmentViewModelFactory(DataRepository()))
            .get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return DataBindingUtil.inflate<MainFragmentBinding>(
            LayoutInflater.from(context),
            R.layout.main_fragment,
            container,
            false
        )
            .apply {
                viewModel = this@MainFragment.viewModel
                mainFragment = this@MainFragment
                setLifecycleOwner(this@MainFragment)
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
