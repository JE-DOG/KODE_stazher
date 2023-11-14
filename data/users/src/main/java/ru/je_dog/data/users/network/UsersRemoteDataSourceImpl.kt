package ru.je_dog.data.users.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.je_dog.core.data.common.ext.networkRequest
import ru.je_dog.core.data.common.util.NetworkConnectionMonitory
import ru.je_dog.core.data.model.UserJson
import ru.je_dog.data.users.network.api.UsersApi

class UsersRemoteDataSourceImpl(
    private val usersApi: UsersApi,
    private val connectionMonitory: NetworkConnectionMonitory
): UsersRemoteDataSource {

    override suspend fun getUsers(): Flow<List<UserJson>> = flow {
        networkRequest(connectionMonitory){
            usersApi.getUsers()
        }
    }

    override suspend fun getUsersDynamic(): Flow<List<UserJson>> = flow {
        networkRequest(connectionMonitory){
            usersApi.getUsersDynamic()
        }
    }

    override suspend fun getUsersWithError(): Flow<List<UserJson>> = flow {
        networkRequest(connectionMonitory){
            usersApi.getUsersWithError()
        }
    }

}