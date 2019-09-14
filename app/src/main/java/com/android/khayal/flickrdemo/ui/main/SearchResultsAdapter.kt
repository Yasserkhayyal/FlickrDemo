package com.android.khayal.flickrdemo.ui.main

import com.android.khayal.flickrdemo.R
import com.android.khayal.flickrdemo.base.BaseAdapter
import com.android.khayal.flickrdemo.vo.CommonViewHolder
import com.android.khayal.flickrdemo.vo.SearchResponse
import kotlin.properties.Delegates

class SearchResultsAdapter<T>(var myItems: Array<T?>? = null):
BaseAdapter<T?>(myItems) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.search_result_item_layout
    }

    override fun getObjectForPosition(position: Int): T? {
        return myItems?.get(position)
    }

    override fun getItemCount(): Int {
        return myItems?.size?:1
    }
}