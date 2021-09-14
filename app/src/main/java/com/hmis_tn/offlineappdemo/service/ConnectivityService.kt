package com.hmis_tn.offlineappdemo.service

import android.os.Bundle

import android.content.Intent

import android.app.IntentService
import android.util.Log

internal class ConnectivityService(name: String?) : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        val extras = intent!!.extras
        val isNetworkConnected = extras!!.getBoolean("isNetworkConnected")
        // your code
        if(isNetworkConnected){
            Log.e("TAGNetwork", "Yes");
        }

    }
}