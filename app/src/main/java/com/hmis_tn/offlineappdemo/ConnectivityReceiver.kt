package com.hmis_tn.offlineappdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.net.NetworkInfo

import android.net.ConnectivityManager

import android.content.Intent



class ConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, arg1: Intent?) {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = (activeNetwork != null
                && activeNetwork.isConnectedOrConnecting)
        if (ConnectivityReceiver.Companion.connectivityReceiverListener != null) {
            ConnectivityReceiver.Companion.connectivityReceiverListener!!.onNetworkConnectionChanged(
                isConnected
            )
        }
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectivityReceiverListener: ConnectivityReceiver.ConnectivityReceiverListener? = null
        val isConnected: Boolean
            get() {
                val cm = MyApplication.instance.applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = cm.activeNetworkInfo
                return (activeNetwork != null
                        && activeNetwork.isConnectedOrConnecting)
            }
    }
}