package com.android.khayal.flickrdemo.main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.khayal.flickrdemo.R
import com.android.khayal.flickrdemo.data.DataRepository
import com.android.khayal.flickrdemo.databinding.MainFragmentBinding
import com.android.khayal.flickrdemo.listeners.RecyclerItemClickListener


class MainFragment : Fragment(), RecyclerItemClickListener.OnRecyclerClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, MainFragmentViewModelFactory(DataRepository()))
            .get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<MainFragmentBinding>(LayoutInflater.from(context), R.layout.main_fragment, container, false)
            .apply {
                viewModel = this@MainFragment.viewModel
                mainFragment = this@MainFragment
                setLifecycleOwner(this@MainFragment)
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSearchData("Android", "Any")
    }


    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(activity, "normal tap at $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Toast.makeText(activity, "long tap at $position", Toast.LENGTH_SHORT).show()
    }
}
