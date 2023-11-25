package ru.je_dog.core.feature.common.ui.elements.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.je_dog.core.feature.R

@Composable
fun ErrorScreen(
    onRepeatClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(id = R.drawable.ic_nlo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(8.dp))
        
        Text(text = "Какой-то сверхразум все сломал")
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(text = "Постараемся быстро починить")
        
        Spacer(modifier = Modifier.height(8.dp))
        
        TextButton(onClick = onRepeatClick ) {
            Text(text = "Попробовать снова")
        }

    }

}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun ErrorScreenPreview() {

    ErrorScreen {

    }

}