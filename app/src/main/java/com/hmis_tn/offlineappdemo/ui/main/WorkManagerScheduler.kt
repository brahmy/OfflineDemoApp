package com.hmis_tn.offlineappdemo.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.*
import com.hmis_tn.offlineappdemo.MyWorker
import java.util.*
import java.util.concurrent.TimeUnit

object WorkManagerScheduler {

    @SuppressLint("IdleBatteryChargingConstraints")
    fun refreshPeriodicWork(context: Context) {

        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        // Set Execution around 07:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 7)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff)

        Log.d("MyWorker", "time difference $minutes"+"network:"+NetworkType.CONNECTED)

        //define constraints
        val myConstraints = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .setRequiresStorageNotLow(true)
                .setRequiresDeviceIdle(true)
                .build()
        } else {
            Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .setRequiresStorageNotLow(true)
                .build()
        }

        val refreshCpnWork = PeriodicWorkRequest.Builder(MyWorker::class.java, 0, TimeUnit.MINUTES)
            .setInitialDelay(0,TimeUnit.MILLISECONDS)
            .setConstraints(myConstraints)
            .addTag("myWorkManager")
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork("myWorkManager",
            ExistingPeriodicWorkPolicy.REPLACE, refreshCpnWork)

    }
}