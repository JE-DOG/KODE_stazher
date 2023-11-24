package ru.je_dog.feature.user_profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.user_profile.UserProfile

fun NavGraphBuilder.userProfile(){

    composable(
        "$userProfileRoute/{user}",
        arguments = listOf(
            navArgument("user"){
                type = NavType.StringType
            }
        )
    ){

        val userJson = it.arguments?.getString("user")
        val user = Gson().fromJson(userJson,UserPresentation::class.java)

        UserProfile(user = user)

    }

}

const val userProfileRoute = "userProfileRoute"