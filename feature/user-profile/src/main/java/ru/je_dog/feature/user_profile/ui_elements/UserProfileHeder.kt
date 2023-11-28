package ru.je_dog.feature.user_profile.ui_elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.je_dog.core.feature.common.ui.theme.LightGray
import ru.je_dog.core.feature.common.ui.theme.Typography
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.user_profile.R

@Composable
fun UserProfileHeader(
    modifier: Modifier = Modifier,
    user: UserPresentation,
    onBackClick: () -> Unit = {}
) {

    Column(
        modifier
            .fillMaxWidth()
            .padding(24.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            modifier = Modifier
                .align(Alignment.Start),
            onClick = onBackClick
        ) {
            Icon(
                painter = painterResource(id = ru.je_dog.core.feature.R.drawable.ic_back),
                contentDescription = null
            )
        }

        Image(
            modifier = Modifier.size(104.dp),
            painter = painterResource(id = ru.je_dog.core.feature.R.drawable.ic_profile),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "${user.firstname} ${user.lastname}",
                style = Typography.bodyLarge
            )
            Text(
                text = " ${user.userTag.lowercase()}",
                style = Typography.bodyMedium,
                color = LightGray
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = user.department,
            style = Typography.bodySmall
        )


    }

}