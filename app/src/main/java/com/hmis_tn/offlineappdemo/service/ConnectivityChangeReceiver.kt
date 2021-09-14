package com.hmis_tn.offlineappdemo.service

import android.content.BroadcastReceiver

import android.net.ConnectivityManager

import android.content.Context

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.hmis_tn.offlineappdemo.ui.main.WorkManagerScheduler


class ConnectivityChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (isOnline(context)) {
            Toast.makeText(context, "Available", Toast.LENGTH_SHORT).show()
            Log.e("TAGNetwork", "Yes");
            startWorkManager(context)
        } else {
            Log.e("TAGNetwork", "NO");
            Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private fun startWorkManager(context: Context) {
        WorkManagerScheduler.refreshPeriodicWork(context)
    }

}
