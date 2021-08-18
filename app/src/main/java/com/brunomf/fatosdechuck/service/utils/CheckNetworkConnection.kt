package com.brunomf.fatosdechuck.service.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object CheckNetworkConnection {
  

    fun isOnline(context: Context): Boolean {

        var result = false

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager?
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        connectivityManager?.run {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            }
        }

        return result
    }

    fun noNetworkConnectivityError(): FactsResult.ConnectionError {
        return FactsResult.ConnectionError
    }

}
