package ru.je_dog.data.users.network

import kotlinx.coroutines.flow.Flow
import ru.je_dog.core.data.model.UserJson

interface UsersRemoteDataSource {

    suspend fun getUsers(): Flow<List<UserJson>>

    suspend fun getUsersDynamic(): Flow<List<UserJson>>

    suspend fun getUsersWithError(): Flow<List<UserJson>>

}