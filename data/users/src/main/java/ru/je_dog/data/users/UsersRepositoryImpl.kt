package ru.je_dog.data.users

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.je_dog.core.domain.model.UserDomain
import ru.je_dog.data.users.network.UsersRemoteDataSource
import ru.je_dog.users.UsersRepository

class UsersRepositoryImpl(
    private val usersRemoteDataSource: UsersRemoteDataSource
): UsersRepository {

    override suspend fun getUsers(): Flow<List<UserDomain>> = usersRemoteDataSource.getUsers()
        .map { usersJson ->
            usersJson.map {
                it.toDomain()
            }
        }

    override suspend fun getDynamicUsers(): Flow<List<UserDomain>> = usersRemoteDataSource.getUsersDynamic()
        .map { usersJson ->
            usersJson.map { it.toDomain() }
        }

    override suspend fun getUsersWithError(): Flow<List<UserDomain>> = usersRemoteDataSource.getUsersWithError()
        .map { usersJson ->
            usersJson.map { it.toDomain() }
        }

}