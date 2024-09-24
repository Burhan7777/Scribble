package com.pzbdownloaders.scribble.common.domain.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class CheckInternet {

    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(network) ?: return false

                return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo ?: return false
                return networkInfo.isConnected
            }
        }
    }
}