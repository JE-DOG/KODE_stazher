package ru.je_dog.core.data.common.util

import kotlinx.coroutines.flow.Flow

interface NetworkConnectionMonitory {

    val isOnline: Flow<Boolean>

    suspend fun isOnlineNow(): Boolean

}