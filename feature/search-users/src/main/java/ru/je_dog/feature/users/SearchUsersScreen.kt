package ru.je_dog.feature.users

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.fengdai.compose.pulltorefresh.PullToRefresh
import com.github.fengdai.compose.pulltorefresh.rememberPullToRefreshState
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ru.je_dog.core.feature.common.ui.elements.error.ErrorScreen
import ru.je_dog.core.feature.common.ui.elements.error.FindError
import ru.je_dog.feature.users.ui_elements.top_app_bar.TopAppBar
import ru.je_dog.feature.users.ui_elements.users_list.UserItem
import ru.je_dog.feature.users.ui_elements.users_list.YearItem
import ru.je_dog.feature.users.ui_elements.users_list.skeleton.SkeletonUserItem
import ru.je_dog.feature.users.vm.SearchUsersViewModel
import ru.je_dog.feature.users.vm.action.FilterByDepartmentsAction
import ru.je_dog.feature.users.vm.action.FilterByInputSearchAction
import ru.je_dog.feature.users.vm.action.RefreshAction
import ru.je_dog.feature.users.vm.action.SortByBirthdayAction
import ru.je_dog.feature.users.vm.action.TryAgainLoadUsersAction
import ru.je_dog.core.feature.common.ui.elements.radio.RadioButton
import ru.je_dog.feature.users.ui_elements.pull_to_refresh.CustomPullToRefreshIndicator
import ru.je_dog.feature.users.vm.action.ClickOnUserItemAction
import ru.je_dog.feature.users.vm.action.SortByNameAction

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SearchUsersScreen(
    viewModel: SearchUsersViewModel = koinInject(),
) {

    val state = viewModel.state.collectAsState().value
    val listModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)

    Log.d("SearchUsersScreenState", "$state")

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetBackgroundColor = Color(0xFFFFFFFF),
        sheetContent = {

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(56.dp)
                    .background(Color(0xFFC3C3C6), RoundedCornerShape(30.dp))
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Сортировка"
            )

            Spacer(modifier = Modifier.height(20.dp))

            RadioButton(
                text = "По алфавиту",
                selected = state.sortType == SortByNameAction.SORT_NAME,
                onClick = {
                    val action = SortByNameAction
                    viewModel.action(action)
                }
            )

            RadioButton(
                text = "По дню рождения",
                selected = state.sortType == SortByBirthdayAction.SORT_NAME,
                onClick = {
                    val action = SortByBirthdayAction
                    viewModel.action(action)
                }
            )

        }
    ) {

        Column(
            Modifier.fillMaxSize()
        ) {

            AnimatedVisibility(!state.isLoading && !state.isError && state.usersList.isNotEmpty()) {

                TopAppBar(
                    modifier = Modifier
                        .padding(top = 6.dp),
                    onSortClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                    onTextChange = {
                        val action = FilterByInputSearchAction(it)
                        viewModel.action(action)
                    },
                    text = state.searchInputFilter ?: "",
                    onCancelClick = { viewModel.action(FilterByInputSearchAction("")) },
                    onTabClick = {
                        val action = FilterByDepartmentsAction(it)
                        viewModel.action(action)
                    },
                    selectedTab = state.departmentFilter,
                    hasSorter = state.sortType == SortByBirthdayAction.SORT_NAME
                )

            }

            if (state.isLoading) {

                LazyColumn(
                    modifier = listModifier
                ) {
                    items(12) {

                        SkeletonUserItem()

                    }
                }

            } else {

                if (!state.isError) {

                    val pullToRefreshState =
                        rememberPullToRefreshState(isRefreshing = state.isRefreshing)

                    PullToRefresh(
                        state = pullToRefreshState,
                        indicator = { state, refreshTriggerDistance, _ ->
                            CustomPullToRefreshIndicator(refreshTriggerDistance, state)
                        },
                        onRefresh = {
                            val action = RefreshAction
                            viewModel.action(action)
                        }
                    ) {

                        if (state.filteredUsersList.isNotEmpty()) {

                            LazyColumn(
                                modifier = listModifier
                            ) {

                                if (state.sortType == SortByBirthdayAction.SORT_NAME) {

                                    val calendar = Calendar.getInstance()
                                    val nextYear = calendar.get(Calendar.YEAR) + 1
                                    val currentMonth = calendar.get(Calendar.MONTH) + 1
                                    val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                                    val filteredUsersList = state.filteredUsersList

                                    val currentYearUsers = filteredUsersList.dropWhile {

                                        Log.d("FilterTag","Current month: $currentDayOfMonth")
                                        Log.d("FilterTag","User month: ${it.birthday.month}")
                                        Log.d("FilterTag","Current day: $currentDayOfMonth")
                                        Log.d("FilterTag","User day: ${it.birthday.day}")
                                        Log.d("FilterTag","Boolean: ${
                                            it.birthday.month < currentMonth
                                                    || 
                                            it.birthday.month == currentMonth && it.birthday.day <= currentDayOfMonth
                                        }\nUser:$it")

                                        it.birthday.month < currentMonth
                                                ||
                                        it.birthday.month == currentMonth && it.birthday.day <= currentDayOfMonth
                                    }

                                    val nextYearUsers =
                                        filteredUsersList.take(filteredUsersList.size - currentYearUsers.size)

                                    items(currentYearUsers) {
                                        UserItem(
                                            user = it,
                                            isSortByBirthday = state.sortType == SortByBirthdayAction.SORT_NAME
                                        ){
                                            val action = ClickOnUserItemAction(it)
                                            viewModel.action(action)
                                        }
                                    }

                                    item {

                                        Log.d("ListTag", "Next year: $nextYear")
                                        YearItem(year = nextYear.toString())

                                    }

                                    items(nextYearUsers) {
                                        UserItem(
                                            user = it,
                                            isSortByBirthday = state.sortType == SortByBirthdayAction.SORT_NAME
                                        ){
                                            val action = ClickOnUserItemAction(it)
                                            viewModel.action(action)
                                        }
                                    }

                                } else {

                                    items(state.filteredUsersList) {
                                        UserItem(
                                            user = it,
                                            isSortByBirthday = state.sortType == SortByBirthdayAction.SORT_NAME
                                        ){
                                            val action = ClickOnUserItemAction(it)
                                            viewModel.action(action)
                                        }
                                    }

                                }

                            }

                        } else {

                            FindError()

                        }

                    }

                } else {

                    ErrorScreen {
                        val action = TryAgainLoadUsersAction
                        viewModel.action(action)
                    }

                }

            }

        }

    }


}