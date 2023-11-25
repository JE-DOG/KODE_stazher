package ru.je_dog.core.feature.common.ui.elements.radio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {

        androidx.compose.material.RadioButton(
            selected = selected,
            onClick = {}
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = text
        )

    }

}