package com.hmis_tn.offlineappdemo.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.ServiceState
import com.hmis_tn.offlineappdemo.utils.Actions

class StartReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ) {
            Intent(context, EndlessService::class.java).also {
                it.action = Actions.START.name
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    println("Starting the service in >=26 Mode from a BroadcastReceiver")
                    context.startForegroundService(it)
                    return
                }
                println("Starting the service in < 26 Mode from a BroadcastReceiver")
                context.startService(it)
            }
        }
    }
}