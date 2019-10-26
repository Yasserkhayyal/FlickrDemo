package com.android.khayal.flickrdemo.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.android.khayal.flickrdemo.ui.CommonViewHolder

open class BaseAdapter<T>(var items: Array<T?>? = null) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CommonViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )
        return CommonViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: CommonViewHolder<T>, position: Int) {
        viewHolder.bind(getObjectForPosition(position))
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    open fun getLayoutIdForPosition(position: Int): Int {
        return 0 //default implementation to be changed in subclasses
    }

    open fun getObjectForPosition(position: Int): T? {
        return items?.get(position)
    }
}