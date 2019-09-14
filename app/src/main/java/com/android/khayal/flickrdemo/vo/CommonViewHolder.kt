package com.android.khayal.flickrdemo.vo

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.android.khayal.flickrdemo.BR


class CommonViewHolder<in T>(val viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(obj: T?){
        viewDataBinding.setVariable(BR.obj,obj)
        viewDataBinding.executePendingBindings()
    }
}