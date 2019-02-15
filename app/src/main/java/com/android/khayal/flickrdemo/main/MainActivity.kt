package com.android.khayal.flickrdemo.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.khayal.flickrdemo.R
import com.android.khayal.flickrdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            setSupportActionBar(toolbar)
            setLifecycleOwner(this@MainActivity)
        }

        if (savedInstanceState == null) {
            initMainFragment()
        }
    }

    private fun initMainFragment() {
        supportFragmentManager.beginTransaction().add(
            R.id.main_activity_container,
            MainFragment.newInstance(), null
        ).commitNow()
    }
}
