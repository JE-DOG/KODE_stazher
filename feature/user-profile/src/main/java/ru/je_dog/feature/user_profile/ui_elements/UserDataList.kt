package ru.je_dog.feature.user_profile.ui_elements

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.user_profile.R

@Composable
fun UserDataList(
    modifier: Modifier,
    user: UserPresentation
) {

    val calendar = Calendar.getInstance()
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentYear = calendar.get(Calendar.YEAR)

    Column(
        modifier
            .fillMaxSize()
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            
            Icon(
                painter = painterResource(id = ru.je_dog.core.feature.R.drawable.ic_star),
                contentDescription = null
            )
            
            Spacer(modifier = Modifier.width(14.dp))
            
            Text(text = "${user.birthday.day} ${user.birthday.monthName()} ${user.birthday.year}")

            Spacer(modifier = Modifier.weight(1f))

            val userOld = if (currentMonth > user.birthday.month || currentMonth == user.birthday.month && currentDay > user.birthday.day){
                currentYear - user.birthday.year
            }else {
                currentYear - user.birthday.year - 1
            }

            Text(text = userOld.toString())

        }

        Divider(modifier = Modifier.padding(vertical = 2.5.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = ru.je_dog.core.feature.R.drawable.ic_phone),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(14.dp))

            Text(text = user.phone)


        }



    }

}