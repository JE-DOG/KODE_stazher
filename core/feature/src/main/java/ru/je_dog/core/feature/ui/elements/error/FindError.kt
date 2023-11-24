package ru.je_dog.core.feature.ui.elements.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.je_dog.core.feature.R

@Composable
fun FindError() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .size(56.dp),
            painter = painterResource(id = R.drawable.image_loupe),
            contentDescription = null
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(text = "Мы никого не нашли")
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(text = "Попробуй скорректировать запрос")

    }

}