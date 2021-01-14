package com.android.khayal.flickrdemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.khayal.flickrdemo.databinding.ActivityMainBinding
import com.android.khayal.flickrdemo.ui.main.MainFragment
import com.android.khayal.flickrdemo.ui.searchable.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        setSupportActionBar(mainActivityBinding.toolbarLayout.toolbar)
        if (savedInstanceState == null) {
            initMainFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.start_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initMainFragment() {
        supportFragmentManager.beginTransaction().add(
            R.id.main_activity_container,
            MainFragment(), null
        ).commitNow()
    }
}
