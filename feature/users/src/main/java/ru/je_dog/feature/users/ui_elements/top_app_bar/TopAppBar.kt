package ru.je_dog.feature.users.ui_elements.top_app_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.je_dog.feature.users.model.DepartmentTab
import ru.je_dog.feature.users.ui_elements.top_app_bar.input_field.InputField
import ru.je_dog.feature.users.ui_elements.top_app_bar.tab_row.DepartmentsTabRow

@Composable
fun TopAppBar(
    onSortClick: () -> String,
    onTextChange: (String) -> Unit,
    text: String,
    onCancelClick: () -> Unit,
    onTabClick: (DepartmentTab?) -> Unit,
    selectedTab: String?
) {

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        InputField(
            onTextChange = onTextChange,
            text = text,
            onSortClick = onSortClick,
            onCancelClick = onCancelClick
        )

        DepartmentsTabRow(
            onTabClick = onTabClick,
            selectedTab = selectedTab
        )

    }

}