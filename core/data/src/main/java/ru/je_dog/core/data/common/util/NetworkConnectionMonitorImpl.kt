package ru.je_dog.core.data.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class NetworkConnectionMonitorImpl(
    private val context: Context
): NetworkConnectionMonitory {

    override val isOnline: Flow<Boolean> = callbackFlow<Boolean> {

        val networkManager = context.getSystemService<ConnectivityManager>()!!

        val callback = object: NetworkCallback() {

            override fun onAvailable(network: Network) {
                channel.trySend(networkManager.isCurrentlyConnected)
            }

            override fun onLost(network: Network) {
                channel.trySend(networkManager.isCurrentlyConnected)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                channel.trySend(networkManager.isCurrentlyConnected)
            }

        }

        networkManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            callback
        )

        awaitClose {
            networkManager.unregisterNetworkCallback(
                callback
            )
        }
    }
        .conflate()

}

val ConnectivityManager.isCurrentlyConnected
    get() = activeNetwork
        ?.let(::getNetworkCapabilities)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ?: false