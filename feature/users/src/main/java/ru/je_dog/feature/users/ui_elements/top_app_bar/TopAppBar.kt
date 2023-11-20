package ru.je_dog.feature.users.ui_elements.top_app_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.je_dog.feature.users.model.DepartmentTab
import ru.je_dog.feature.users.ui_elements.top_app_bar.tab_row.DepartmentsTabRow

@Composable
fun TopAppBar(
    currentDepartment: String? = null,
    currentSortType: String,
    onTextChange: (String) -> Unit,
    onTabClick: (DepartmentTab?) -> Unit,
    selectedTab: DepartmentTab?
) {

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {


        DepartmentsTabRow(
            onTabClick = onTabClick,
            selectedTab = selectedTab
        )
    }

}