package ru.je_dog.feature.users.ui_elements.users_list.skeleton

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SkeletonUserItem() {

    Row(
        Modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier.fillMaxHeight(),
            painter = painterResource(id = ru.je_dog.core.feature.R.drawable.ic_profile),
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            Modifier
                .wrapContentSize()
        ) {

            Box(
                Modifier
                    .width(144.dp)
                    .height(16.dp)
                    .background(
                        Color(0xFFF7F7F8),
                        RoundedCornerShape(50.dp)
                    )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                Modifier
                    .width(80.dp)
                    .height(12.dp)
                    .background(
                        Color(0xFFF7F7F8),
                        RoundedCornerShape(50.dp)
                    )
            )

        }

    }

}