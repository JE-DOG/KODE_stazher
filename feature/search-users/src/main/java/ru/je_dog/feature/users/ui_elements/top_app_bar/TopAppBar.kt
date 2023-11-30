package ru.je_dog.feature.users.ui_elements.top_app_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.je_dog.feature.users.model.UsersDepartmentTab
import ru.je_dog.feature.users.ui_elements.top_app_bar.input_field.InputField
import ru.je_dog.feature.users.ui_elements.top_app_bar.tab_row.DepartmentsTabRow

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onSortClick: () -> Unit,
    onTextChange: (String) -> Unit,
    text: String,
    onCancelClick: () -> Unit,
    hasSorter: Boolean,
    onTabClick: (UsersDepartmentTab) -> Unit,
    selectedTab: UsersDepartmentTab
) {

    Column(
        modifier
    ) {

        InputField(
            Modifier
                .padding(horizontal = 16.dp),
            onTextChange = onTextChange,
            text = text,
            onSortClick = onSortClick,
            onCancelClick = onCancelClick,
            hasSorter = hasSorter
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        DepartmentsTabRow(
            onTabClick = onTabClick,
            selectedTab = selectedTab
        )

    }

}