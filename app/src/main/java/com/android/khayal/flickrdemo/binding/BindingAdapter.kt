package com.android.khayal.flickrdemo.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.khayal.flickrdemo.listeners.RecyclerItemClickListener
import com.android.khayal.flickrdemo.ui.main.SearchResultsAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingAdapter {

    @Suppress("UNCHECKED_CAST")
    @BindingAdapter(value = ["recyclerViewData", "recyclerItemClickListener"], requireAll = false)
    @JvmStatic
    fun <T> setRecyclerViewData(
        view: View, dataArray: Array<T?>? = null,
        recyclerViewItemClickListener: RecyclerItemClickListener.OnRecyclerClickListener?
    ) {
        val recyclerView = view as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(
            view.context,
            RecyclerView.VERTICAL,
            false
        )
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
        val recyclerViewAdapter = recyclerView.adapter as? SearchResultsAdapter<T>
        recyclerViewAdapter?.apply {
            myItems = dataArray
            recyclerViewAdapter.notifyDataSetChanged()
        } ?: SearchResultsAdapter(dataArray).let {
            recyclerView.adapter = it
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

    @BindingAdapter("snackbarVisibility")
    @JvmStatic
    fun setSnackBarVisibility(view: View, error: String?) {
        view.visibility = if (error.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}