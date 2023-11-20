package ru.je_dog.feature.users.ui_elements.top_app_bar.tab_row

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import ru.je_dog.core.feature.R
import ru.je_dog.feature.users.model.DepartmentTab

@Composable
fun DepartmentsTabRow(
    onTabClick: (DepartmentTab?) -> Unit,
    selectedTab: String?
) {

    val departments = stringArrayResource(id = R.array.departments)
        .map {
            val tabAndName = it.split('-')

            DepartmentTab(
                tabAndName[0],
                tabAndName[1]
            )
        }

    ScrollableTabRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        selectedTabIndex = 0,
        indicator = { tabPositions ->

            val department = departments.find {
                selectedTab == it.name
            }
            val index = if (department != null)
                departments.lastIndexOf(department)
            else
                null

            Box(
                Modifier
                    .tabIndicatorOffset(
                        if (index == null)
                            tabPositions[0]
                        else
                            tabPositions[index]
                    )
                    .padding(10.dp)
                    .background(Color(0xFF6534FF))
            )
            
        }
    ) {

        Tab(
            selected = selectedTab == null,
            onClick = {
                onTabClick(null)
            }
        ) {
            Text(text = "Все")
        }

        departments.forEach { department ->

            Tab(
                selected = selectedTab == department.name,
                onClick = {
                    onTabClick(department)
                }
            ) {
                Text(text = department.name)
            }

        }
        
    }

}