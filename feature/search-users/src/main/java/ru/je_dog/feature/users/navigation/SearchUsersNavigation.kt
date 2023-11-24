package ru.je_dog.feature.users.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.je_dog.feature.users.SearchUsersScreen

const val searchUsersRoute = "searchUsersRoute"

fun NavGraphBuilder.searchUsers(){

    composable(searchUsersRoute){

        SearchUsersScreen()

    }

}

