package ru.je_dog.data.users.network

import kotlinx.coroutines.flow.Flow
import ru.je_dog.core.data.model.user.UserJson
import ru.je_dog.core.data.model.user.UserJsonList

interface UsersRemoteDataSource {

    suspend fun getUsers(): Flow<UserJsonList>

    suspend fun getUsersDynamic(): Flow<UserJsonList>

    suspend fun getUsersWithError(): Flow<UserJsonList>

}