package com.android.khayal.flickrdemo.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

object SearchResponse {
    data class Content(val items: Array<Item>)
    @Entity(tableName = "search_results")
    data class Item(
        var title: String,
        @PrimaryKey var link: String,
        @Embedded var media: Media,
        var date_taken: String,
        var description: String,
        var published: String,
        var author: String,
        var author_id: String,
        var tags: String,
        var tagMode: String
    )

    @Entity(tableName = "media")
    data class Media(@SerializedName("m") @PrimaryKey var mediaURL: String = "")
}