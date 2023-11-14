package ru.je_dog.core.data.common.exception

class NoSuccessesRequestException(
    val errorCode: Int
): Exception()