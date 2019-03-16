package com.android.khayal.flickrdemo.listeners

import android.content.Intent

interface NewIntentListener {
    fun onNewIntentReceived(newIntent: Intent)
}