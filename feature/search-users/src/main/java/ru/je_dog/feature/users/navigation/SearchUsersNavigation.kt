package ru.je_dog.feature.users.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.SearchUsersScreen

const val searchUsersRoute = "searchUsersRoute"

fun NavGraphBuilder.searchUsers(
    navigateToUserProfile: (UserPresentation) -> Unit
){

    composable(searchUsersRoute){

        SearchUsersScreen(navigateToUserProfile)

    }

}

