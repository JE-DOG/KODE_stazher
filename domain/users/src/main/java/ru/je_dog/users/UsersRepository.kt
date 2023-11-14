package ru.je_dog.users

import kotlinx.coroutines.flow.Flow
import ru.je_dog.core.domain.model.UserDomain

interface UsersRepository {

    suspend fun getUsers(): Flow<List<UserDomain>>

    suspend fun getDynamicUsers(): Flow<List<UserDomain>>

    suspend fun getUsersWithError(): Flow<List<UserDomain>>

}