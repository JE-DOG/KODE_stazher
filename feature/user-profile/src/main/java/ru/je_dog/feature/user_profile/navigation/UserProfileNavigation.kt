package ru.je_dog.feature.user_profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.user_profile.UserProfile

fun NavGraphBuilder.userProfile(
    navigateToBack: () -> Unit
){

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

        UserProfile(
            user = user,
            navigateToBack = navigateToBack
        )

    }

}

fun NavController.navigateToUserProfile(
    userPresentation: UserPresentation
){

    val userJson = Gson().toJson(userPresentation)
    navigate("userProfileRoute/$userJson")

}

const val userProfileRoute = "userProfileRoute"