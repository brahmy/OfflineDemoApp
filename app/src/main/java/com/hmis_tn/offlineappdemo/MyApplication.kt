package com.hmis_tn.offlineappdemo

import android.app.Application
import com.hmis_tn.offlineappdemo.ConnectivityReceiver.ConnectivityReceiverListener


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MyApplication.Companion.mInstance = this
    }

    fun setConnectivityListener(listener: ConnectivityReceiverListener?) {
        ConnectivityReceiver.connectivityReceiverListener = listener
    }

    companion object {
        private var mInstance: MyApplication? = null

        @get:Synchronized
        val instance: MyApplication
            get() = MyApplication.Companion.mInstance!!
    }
}