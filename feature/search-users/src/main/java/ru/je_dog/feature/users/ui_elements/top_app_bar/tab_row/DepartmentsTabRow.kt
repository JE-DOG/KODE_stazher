package ru.je_dog.feature.users.ui_elements.top_app_bar.tab_row

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.je_dog.core.feature.common.ui.theme.Black
import ru.je_dog.core.feature.common.ui.theme.LightGray
import ru.je_dog.core.feature.common.ui.theme.Typography
import ru.je_dog.feature.users.model.UsersDepartmentTab

@Composable
fun DepartmentsTabRow(
    onTabClick: (UsersDepartmentTab) -> Unit,
    selectedTab: UsersDepartmentTab
) {

    val departments = UsersDepartmentTab.values()

    val selectedTabIndex = selectedTab.ordinal

    ScrollableTabRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        edgePadding = 0.dp,
        selectedTabIndex = selectedTabIndex,
        contentColor = Black,
        divider = {},
        indicator = { tabPositions ->

            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp))
            )

        }
    ) {

        departments.forEach { department ->

            DepartmentTab(
                isSelected = selectedTabIndex == department.ordinal,
                onClick = onTabClick,
                department = department
            )

        }

    }
}


@Composable
fun DepartmentTab(
    isSelected: Boolean,
    onClick: (UsersDepartmentTab) -> Unit,
    department: UsersDepartmentTab
) {

    Tab(
        modifier = Modifier
            .padding(horizontal = 12.dp),
        selected = isSelected,
        onClick = {
            onClick(department)
        },
        selectedContentColor = Black,
        unselectedContentColor = LightGray
    ) {
        val fontWeight = if (isSelected)
            FontWeight.Bold
        else
            FontWeight.Normal

        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = department.text,
            style = Typography.bodyMedium,
            fontWeight = fontWeight
        )
    }

}