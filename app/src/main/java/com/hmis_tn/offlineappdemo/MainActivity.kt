package com.hmis_tn.offlineappdemo

import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.SeekBar
import android.widget.Switch
import android.widget.Toast
import com.hmis_tn.offlineappdemo.service.EndlessService
import com.hmis_tn.offlineappdemo.ui.main.MainFragment
import com.hmis_tn.offlineappdemo.utils.Actions
import android.net.ConnectivityManager

import android.content.IntentFilter
import com.hmis_tn.offlineappdemo.service.ConnectivityChangeReceiver


class MainActivity : AppCompatActivity(),ConnectivityReceiver.ConnectivityReceiverListener {

    private var mScheduler: JobScheduler? = null

    // Switches for setting job options.
    private var mDeviceIdleSwitch: Switch? = null
    private var mDeviceChargingSwitch: Switch? = null

    // Override deadline seekbar.
    private var mSeekBar: SeekBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        checkConnection()

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        Constants.isConnected=isConnected
    }

    // Method to manually check connection status
    private fun checkConnection() {
        val isConnected: Boolean = ConnectivityReceiver.isConnected
        Constants.isConnected=isConnected
        actionOnService(Actions.START)
//        Toast.makeText(this,"Hey:"+isConnected,Toast.LENGTH_LONG).show()
    }

    private fun actionOnService(action: Actions) {
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

//        if (getServiceState(this) == ServiceState.STOPPED && action == Actions.STOP) return

        if(action == Actions.STOP) return
        Intent(this, EndlessService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("Me","Starting the service in >=26 Mode")
                startForegroundService(it)
                return
            }
            Log.d("Me","Starting the service in < 26 Mode")
            startService(it)
        }
    }

    override fun onResume() {
        super.onResume()

        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(ConnectivityChangeReceiver(), filter)

    }

    override fun onPause() {
        super.onPause()
//        unregisterReceiver(ConnectivityChangeReceiver())
    }

}
