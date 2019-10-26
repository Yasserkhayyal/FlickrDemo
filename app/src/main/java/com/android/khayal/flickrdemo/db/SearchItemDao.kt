package com.android.khayal.flickrdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.khayal.flickrdemo.vo.SearchResponse

@Dao
interface SearchItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchItems(items: Array<SearchResponse.Item>)

    @Query("SELECT * FROM search_results where tags like :searchTags AND tagMode like :tagMode")
    fun getSearchesByTags(searchTags: String, tagMode: String): LiveData<Array<SearchResponse.Item>>
}