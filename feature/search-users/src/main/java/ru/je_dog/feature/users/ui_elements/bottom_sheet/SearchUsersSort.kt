package ru.je_dog.feature.users.ui_elements.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.je_dog.core.feature.common.ui.elements.radio.RadioButtonWithText
import ru.je_dog.core.feature.common.ui.theme.BottomSheetShape
import ru.je_dog.core.feature.common.ui.theme.Typography
import ru.je_dog.feature.users.model.SearchUsersSortType

@Composable
fun SearchUsersSort(
    currentSortType: SearchUsersSortType,
    onSortTypeClick: (SearchUsersSortType) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp)
            .background(
                Color(0xFFFFFFFF),
                BottomSheetShape
            )
    ) {

        val searchTypes = SearchUsersSortType.values()

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .height(4.dp)
                .width(56.dp)
                .background(Color(0xFFC3C3C6), RoundedCornerShape(30.dp))
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Сортировка",
            style = Typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        searchTypes.forEach { sortType ->

            RadioButtonWithText(
                text = sortType.text,
                selected = currentSortType == sortType,
                onClick = {
                    onSortTypeClick(sortType)
                }
            )

        }
        
        Spacer(modifier = Modifier.height(20.dp))

    }

}