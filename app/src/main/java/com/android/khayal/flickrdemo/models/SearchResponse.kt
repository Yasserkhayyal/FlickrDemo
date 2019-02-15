package com.android.khayal.flickrdemo.models

import com.google.gson.annotations.SerializedName

object SearchResponse {
    data class Content(val items: Array<Item>)
    data class Item(
        var title: String = "",
        var link: String = "",
        var media: Media,
        var date_taken: String = "",
        var description: String = "",
        var published: String = "",
        var author: String = "",
        var author_id: String = "",
        var tags: String = ""
    )
    data class Media(@SerializedName("m") var mediaURL:String="")
}