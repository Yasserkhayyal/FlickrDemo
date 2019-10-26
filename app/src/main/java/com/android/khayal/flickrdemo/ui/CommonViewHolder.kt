package com.android.khayal.flickrdemo.ui

import androidx.databinding.ViewDataBinding
import com.android.khayal.flickrdemo.BR


class CommonViewHolder<in T>(val viewDataBinding: ViewDataBinding) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(obj: T?) {
        viewDataBinding.setVariable(BR.obj, obj)
        viewDataBinding.executePendingBindings()
    }
}