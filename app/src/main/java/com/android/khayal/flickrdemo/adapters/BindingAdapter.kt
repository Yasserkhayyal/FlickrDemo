package com.android.khayal.flickrdemo.adapters

import android.arch.lifecycle.LiveData
import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.android.khayal.flickrdemo.listeners.RecyclerItemClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.observers.TestObserver

object BindingAdapter {

    @Suppress("UNCHECKED_CAST")
    @BindingAdapter(value = ["recyclerViewData", "recyclerItemClickListener"], requireAll = false)
    @JvmStatic
    fun <T> setRecyclerViewData(
        view: View, dataArray: Array<T?>? = null,
        recyclerViewItemClickListener: RecyclerItemClickListener.OnRecyclerClickListener?
    ) {
        val recyclerView = view as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        if (recyclerView.adapter == null) { // workaround to prevent adding onItemTouchListener every time the underlying
            //dataSet changes
            recyclerViewItemClickListener?.run {
                recyclerView.addOnItemTouchListener(
                    RecyclerItemClickListener(
                        view.context,
                        recyclerView, recyclerViewItemClickListener
                    )
                )
            }
        }
        recyclerView.setHasFixedSize(true)
        var recyclerViewAdapter = recyclerView.adapter as? SearchResultsAdapter<T>
        recyclerViewAdapter?.apply {
            items = dataArray
            recyclerViewAdapter?.notifyDataSetChanged()
        } ?: SearchResultsAdapter(dataArray).let {
            recyclerViewAdapter = it
            recyclerView.adapter = recyclerViewAdapter
        }
    }

    @BindingAdapter("imageSrcUrl")
    @JvmStatic
    fun setImageUrl(view: View, url: String?) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions.overrideOf(view.width, view.height))
            .into(view as ImageView)
    }

    @BindingAdapter("setVisibility")
    @JvmStatic
    fun showProgressBar(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

}