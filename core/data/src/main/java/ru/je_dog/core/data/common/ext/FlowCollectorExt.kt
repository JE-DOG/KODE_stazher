package ru.je_dog.core.data.common.ext

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.last
import retrofit2.Response
import ru.je_dog.core.data.common.exception.NoInternetException
import ru.je_dog.core.data.common.exception.NoSuccessesRequestException
import ru.je_dog.core.data.common.util.NetworkConnectionMonitory

suspend inline fun<T> FlowCollector<T>.networkRequest(
    connectionMonitory: NetworkConnectionMonitory,
    request: () -> Response<T>
){

    if (connectionMonitory.isOnline.last()){
        val result = request()

        if (result.isSuccessful)
            emit(result.body()!!)
        else
            throw NoSuccessesRequestException(result.code())
    }else
        throw NoInternetException

}