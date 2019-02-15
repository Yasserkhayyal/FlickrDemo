package com.android.khayal.flickrdemo.adapters

import com.android.khayal.flickrdemo.R

class SearchResultsAdapter<T>(override var items: Array<T?>? = null):
BaseAdapter<T?>(items) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.search_result_item_layout
    }

    override fun getObjectForPosition(position: Int): T? {
        return items?.get(position)
    }
}