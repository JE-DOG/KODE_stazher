package ru.je_dog.feature.users.ui_elements.users_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.je_dog.core.feature.R
import ru.je_dog.core.feature.common.ui.theme.Gray
import ru.je_dog.core.feature.common.ui.theme.Typography
import ru.je_dog.core.feature.model.UserPresentation

@Composable
fun UserItem(
    user: UserPresentation,
    isSortByBirthday: Boolean,
    onClick: (UserPresentation) -> Unit
) {

    Row(
        Modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(vertical = 6.dp)
            .clickable {
                onClick(user)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier.fillMaxHeight(),
            painter = painterResource(id = R.drawable.ic_profile),
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            Modifier
                .wrapContentSize()
        ) {

            Row(
                Modifier
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${user.firstname} ${user.lastname}",
                    style = Typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = user.userTag.lowercase(),
                    style = Typography.bodySmall
                )

            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = user.department,
                color = Gray,
                style = Typography.bodySmall
            )

        }

        if (isSortByBirthday){

            Spacer(modifier = Modifier.weight(1f))

            val userBirthMonth = user.birthday.monthName().take(3)
            Text(
                text = "${user.birthday.day} $userBirthMonth",
                color = Gray,
                style = Typography.bodySmall
            )

        }

    }

}