package ru.je_dog.feature.users

import android.icu.util.Calendar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.fengdai.compose.pulltorefresh.PullToRefresh
import com.github.fengdai.compose.pulltorefresh.rememberPullToRefreshState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.je_dog.core.feature.common.ui.elements.error.ErrorScreen
import ru.je_dog.core.feature.common.ui.elements.error.FindError
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.ui_elements.top_app_bar.TopAppBar
import ru.je_dog.feature.users.ui_elements.users_list.UserItem
import ru.je_dog.feature.users.ui_elements.users_list.YearItem
import ru.je_dog.feature.users.ui_elements.users_list.skeleton.SkeletonUserItem
import ru.je_dog.feature.users.vm.SearchUsersViewModel
import ru.je_dog.feature.users.vm.action.FilterByDepartmentsAction
import ru.je_dog.feature.users.vm.action.FilterByInputSearchAction
import ru.je_dog.feature.users.vm.action.RefreshAction
import ru.je_dog.feature.users.vm.action.LoadUsersAction
import ru.je_dog.feature.users.model.SearchUsersSortType
import ru.je_dog.feature.users.ui_elements.bottom_sheet.SearchUsersSort
import ru.je_dog.feature.users.ui_elements.pull_to_refresh.CustomPullToRefreshIndicator
import ru.je_dog.feature.users.vm.action.SortByAction

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SearchUsersScreen(
    navigateToUserProfile: (UserPresentation) -> Unit,
    viewModel: SearchUsersViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsState().value
    val listModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {

            SearchUsersSort(
                currentSortType = state.sortType,
                onSortTypeClick = {
                    val action = SortByAction(it)
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
                        .fillMaxWidth()
                        .padding(top = 8.dp),
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
                    hasSorter = state.sortType != SearchUsersSortType.Alphabet
                )

            }

            if (!state.isLoading) {

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

                                when (state.sortType) {

                                    SearchUsersSortType.Birthday -> {

                                        val calendar = Calendar.getInstance()

                                        val nextYear = calendar.get(Calendar.YEAR) + 1
                                        val currentMonth = calendar.get(Calendar.MONTH) + 1
                                        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                                        val filteredUsersList = state.filteredUsersList

                                        val currentYearUsers = filteredUsersList.dropWhile {
                                            it.birthday.month < currentMonth
                                                    ||
                                                    it.birthday.month == currentMonth && it.birthday.day <= currentDayOfMonth
                                        }

                                        val nextYearUsers =
                                            filteredUsersList.take(filteredUsersList.size - currentYearUsers.size)

                                        items(currentYearUsers) {
                                            UserItem(
                                                user = it,
                                                isSortByBirthday = state.sortType == SearchUsersSortType.Birthday,
                                                onClick = navigateToUserProfile
                                            )
                                        }

                                        item {

                                            YearItem(year = nextYear.toString())

                                        }

                                        items(nextYearUsers) {
                                            UserItem(
                                                user = it,
                                                isSortByBirthday = state.sortType == SearchUsersSortType.Birthday,
                                                onClick = navigateToUserProfile
                                            )
                                        }

                                    }

                                    SearchUsersSortType.Alphabet -> {

                                        items(state.filteredUsersList) {
                                            UserItem(
                                                user = it,
                                                isSortByBirthday = state.sortType == SearchUsersSortType.Birthday,
                                                onClick = navigateToUserProfile
                                            )
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
                        val action = LoadUsersAction
                        viewModel.action(action)
                    }

                }

            } else {

                SkeletonList(listModifier)

            }

        }

    }

}

@Composable
private fun SkeletonList(
    modifier: Modifier
) {

    LazyColumn(
        modifier = modifier
    ) {
        items(12) {

            SkeletonUserItem()

        }
    }

}