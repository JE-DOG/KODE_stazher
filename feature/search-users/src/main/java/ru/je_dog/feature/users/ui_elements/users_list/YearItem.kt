package ru.je_dog.feature.users.ui_elements.users_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun YearItem(
    year: String,
    color: Color = Color(0xFFC3C3C6)
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            Modifier
                .width(72.dp)
                .height(5.dp)
                .background(color = color)
        )

        Text(text = year, color = color)

        Box(
            Modifier
                .width(72.dp)
                .height(5.dp)
                .background(color = color)
        )

    }

}