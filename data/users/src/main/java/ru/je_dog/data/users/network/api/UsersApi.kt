package ru.je_dog.data.users.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import ru.je_dog.core.data.model.UserJson

interface UsersApi {

    @GET("users")
    @Headers(
        "Prefer: code=200, example=success"
    )
    fun getUsers(): Response<List<UserJson>>

    @GET("users")
    @Headers(
        "Prefer: code=200, dynamic=true"
    )
    fun getUsersDynamic(): Response<List<UserJson>>

    @GET("users")
    @Headers(
        "Prefer: code=500, example=error-500"
    )
    fun getUsersWithError(): Response<List<UserJson>>

}