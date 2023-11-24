package ru.je_dog.data.users.network.api

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import ru.je_dog.core.data.model.user.UserJson
import ru.je_dog.core.data.model.user.UserJsonList

interface UsersApi {

    @GET("users")
    @Headers(
        "Prefer: code=200, example=success"
    )
    suspend fun getUsers(): Response<UserJsonList>

    @GET("users")
    @Headers(
        "Prefer: code=200, dynamic=true"
    )
    suspend fun getUsersDynamic(): Response<UserJsonList>

    @GET("users")
    @Headers(
        "Prefer: code=500, example=error-500"
    )
    suspend fun getUsersWithError(): Response<UserJsonList>

}