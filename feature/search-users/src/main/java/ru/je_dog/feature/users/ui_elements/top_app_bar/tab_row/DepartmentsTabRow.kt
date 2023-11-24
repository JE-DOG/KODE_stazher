package ru.je_dog.feature.users.ui_elements.top_app_bar.tab_row

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
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
                tabAndName[1],
                tabAndName[0]
            )
        }
    val selectedTabIndex = if (selectedTab == null)
        0
    else
        departments.indexOfFirst { it.tag == selectedTab } + 1

    ScrollableTabRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        edgePadding = 0.dp,
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->

            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Color.Red, RoundedCornerShape(30.dp))
            )

        }
    ) {

        Tab(
            selected = selectedTab == null,
            onClick = {
                onTabClick(null)
            }
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = "Все",
                fontWeight = if (selectedTab == null)
                    FontWeight.Bold
                else
                    FontWeight.Normal
            )
        }

        departments.forEach { department ->

            Tab(
                selected = selectedTab == department.tag,
                onClick = {
                    onTabClick(department)
                }
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = department.name,
                    fontWeight = if (selectedTab == department.tag)
                        FontWeight.Bold
                    else
                        FontWeight.Normal
                )
            }

        }
        
    }

}