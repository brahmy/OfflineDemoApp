package com.hmis_tn.offlineappdemo

import android.R

import android.app.NotificationManager

import android.app.NotificationChannel

import android.content.Context.NOTIFICATION_SERVICE

import androidx.core.content.ContextCompat.getSystemService

import android.app.job.JobParameters

import androidx.core.app.NotificationCompat

import android.app.PendingIntent

import android.content.Intent

import android.app.job.JobService
import android.graphics.Color
import android.os.Build

class NotificationJobService : JobService() {
    // Notification manager.
    var mNotifyManager: NotificationManager? = null

    /**
     * Called by the system once it determines it is time to run the job.
     *
     * @param jobParameters Contains the information about the job.
     * @return Boolean indicating whether or not the job was offloaded to a
     * separate thread.
     * In this case, it is false since the notification can be posted on
     * the main thread.
     */
    override fun onStartJob(jobParameters: JobParameters): Boolean {

        // Create the notification channel.
        createNotificationChannel()

        // Set up the notification content intent to launch the app when
        // clicked.
        val contentPendingIntent = PendingIntent.getActivity(
            this, 0, Intent(
                this,
                MainActivity::class.java
            ),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NotificationJobService.Companion.PRIMARY_CHANNEL_ID)
                .setContentTitle("job_service")
                .setContentText("job_running")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_notification_overlay)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
        mNotifyManager!!.notify(0, builder.build())
        return false
    }

    /**
     * Called by the system when the job is running but the conditions are no
     * longer met.
     * In this example it is never called since the job is not offloaded to a
     * different thread.
     *
     * @param jobParameters Contains the information about the job.
     * @return Boolean indicating whether the job needs rescheduling.
     */
    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    fun createNotificationChannel() {

        // Create a notification manager object.
        mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(
                NotificationJobService.Companion.PRIMARY_CHANNEL_ID, "job_service_notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "notification_channel_description"
            mNotifyManager!!.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        // Notification channel ID.
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}