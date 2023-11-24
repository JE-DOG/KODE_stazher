package ru.je_dog.core.data.common.ext

import android.util.Log
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.last
import retrofit2.Response
import ru.je_dog.core.data.common.exception.NoInternetException
import ru.je_dog.core.data.common.exception.FailedRequestException
import ru.je_dog.core.data.common.util.NetworkConnectionMonitory

suspend inline fun<T> FlowCollector<T>.networkRequest(
    connectionMonitory: NetworkConnectionMonitory,
    request: () -> Response<T>
){

    Log.d("NetworkRequest","Start")

    if (connectionMonitory.isOnlineNow()){
        Log.d("NetworkRequest","Is online")
        val result = request()

        if (result.isSuccessful) {
            Log.d("NetworkRequest", "Is is successful\nDate: ${result.body()}")
            emit(result.body()!!)
        }else {
            Log.d("NetworkRequest", "Is is failed")
            throw FailedRequestException(result.code())
        }
    }else{
        Log.d("NetworkRequest","Is offline")
        throw NoInternetException
    }

}