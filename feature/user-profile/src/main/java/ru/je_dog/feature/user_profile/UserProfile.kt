package ru.je_dog.feature.user_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.koinInject
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.user_profile.ui_elements.UserDataList
import ru.je_dog.feature.user_profile.ui_elements.UserProfileHeader

@Composable
internal fun UserProfile(
    user: UserPresentation,
    navController: NavController = koinInject()
) {

    Column(
        Modifier.fillMaxSize()
    ) {
        
        UserProfileHeader(
            modifier = Modifier
                .background(Color.Gray),
            user = user,
            onBackClick = {
                navController.popBackStack()
            }
        )
        
        UserDataList(
            modifier = Modifier
                .padding(horizontal = 18.dp),
            user = user
        )
        
    }

}