package com.android.khayal.flickrdemo.ui.searchable

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.android.khayal.flickrdemo.R
import com.android.khayal.flickrdemo.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var activitySearchBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(activitySearchBinding.root)
        setSupportActionBar(activitySearchBinding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_activity_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu.findItem(R.id.app_bar_search).actionView as SearchView)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)
        searchView.isSubmitButtonEnabled = true
        searchView.isIconifiedByDefault = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                sharedPreferences.edit().putString(getString(R.string.query_key), query).apply()
                searchView.clearFocus()
                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
}
