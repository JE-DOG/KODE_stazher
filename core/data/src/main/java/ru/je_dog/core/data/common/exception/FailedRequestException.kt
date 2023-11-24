package ru.je_dog.core.data.common.exception

class FailedRequestException(
    val errorCode: Int
): Exception()