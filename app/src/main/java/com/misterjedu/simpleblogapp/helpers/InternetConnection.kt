package com.misterjedu.simpleblogapp.helpers

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NetWorkConnection(private val context: Context) : LiveData<Boolean>() {

    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    private lateinit var networkCallBack: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        updateConnection()

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallBack())
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                lollipopNetworkRequest()
            }
            else -> {
                context.registerReceiver(
                    networkReciever,
                    IntentFilter()
                )
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(connectivityManagerCallBack())
        } else {
            context.unregisterReceiver(networkReciever)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkRequest() {
        val requestBuilder =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)

        connectivityManager.registerNetworkCallback(
            requestBuilder.build(),
            connectivityManagerCallBack()
        )

    }

    private fun connectivityManagerCallBack(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallBack = object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }

            return networkCallBack
        } else {
            throw IllegalAccessError("Error")
        }
    }

    fun hasInternetAccess(): Boolean {
        try {
            val urlc: HttpURLConnection = URL("http://clients3.google.com/generate_204")
                .openConnection() as HttpURLConnection
            urlc.setRequestProperty("User-Agent", "Android")
            urlc.setRequestProperty("Connection", "close")
            urlc.connectTimeout = 1500
            urlc.connect()

            return urlc.responseCode == 204 && urlc.contentLength == 0
        } catch (e: IOException) {
            Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show()
            Log.e("Network Connection", "Error checking internet connection", e)
        }
        return false
    }

    private val networkReciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateConnection()
        }

    }

    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)

    }

}


//Change Page based on internet connection
//        val networkConnection = NetWorkConnection(activity?.applicationContext!!)
//        networkConnection.observe(requireActivity(), {
//            if (isFragmentVisible) {
//                if(it){
//                   isConnection = true
//                    viewModel.getAllPosts()
//                    Toast.makeText(requireContext(), isConnection.toString(), Toast.LENGTH_SHORT).show()
//                }else{
//                   isConnection = false
//                    Toast.makeText(requireContext(), isConnection.toString(), Toast.LENGTH_SHORT).show()
//                }
//            }
//        })