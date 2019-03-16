package com.android.khayal.flickrdemo.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.android.khayal.flickrdemo.R
import com.android.khayal.flickrdemo.databinding.ActivityMainBinding
import com.android.khayal.flickrdemo.searchable.SearchActivity
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            setLifecycleOwner(this@MainActivity)
        }

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
            MainFragment.newInstance(), null
        ).commitNow()
    }
}
