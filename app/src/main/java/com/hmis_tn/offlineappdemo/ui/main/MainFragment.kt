package com.hmis_tn.offlineappdemo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hmis_tn.offlineappdemo.Constants
import com.hmis_tn.offlineappdemo.Constants.Companion.isConnected
import com.hmis_tn.offlineappdemo.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

//        Toast.makeText(requireContext(),"Net::"+isConnected, Toast.LENGTH_LONG).show()

/*
        if(isConnected) {
            startWorkManager()
        }
*/
    }

    private fun startWorkManager() {
        WorkManagerScheduler.refreshPeriodicWork(requireActivity())
    }

}