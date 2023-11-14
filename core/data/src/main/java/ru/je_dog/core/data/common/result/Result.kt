package ru.je_dog.core.data.common.result

sealed interface Result<out T>{

    data class Success<T>(val data: T) : Result<T>

    data class Error(val exception: Throwable? = null) : Result<Nothing>

    object Loading : Result<Nothing>

    object NoInternetConnection: Result<Nothing>

}