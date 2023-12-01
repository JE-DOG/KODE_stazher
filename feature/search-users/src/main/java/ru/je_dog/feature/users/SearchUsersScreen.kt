package ru.je_dog.feature.users

import android.icu.util.Calendar
import android.util.Log
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
import ru.je_dog.feature.users.list.sort.sort_items.AlphabeticallySortItem
import ru.je_dog.feature.users.list.sort.sort_items.BirthdaySortItem
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
                    text = state.searchInputFilter,
                    onCancelClick = { viewModel.action(FilterByInputSearchAction("")) },
                    onTabClick = {
                        val action = FilterByDepartmentsAction(it)
                        viewModel.action(action)
                    },
                    selectedTab = state.departmentFilter,
                    hasSorter = state.sortType != SearchUsersSortType.Alphabet.sorterItem
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

                                val filteredUsersList = state.filteredUsersList

                                when (state.sortType) {

                                    is BirthdaySortItem -> {

                                        val currentYearPoint = state.sortType.currentYearPoint

                                        items(filteredUsersList.size - currentYearPoint) { userIndex ->

                                            val user = filteredUsersList[userIndex + currentYearPoint]

                                            UserItem(
                                                user = user,
                                                isSortByBirthday = state.sortType is BirthdaySortItem,
                                                onClick = navigateToUserProfile
                                            )
                                        }

                                        item {

                                            val calendar = Calendar.getInstance()
                                            val nextYear = calendar.get(Calendar.YEAR) + 1

                                            YearItem(year = nextYear.toString())

                                        }

                                        items(currentYearPoint) { userIndex ->

                                            val user = filteredUsersList[userIndex]

                                            UserItem(
                                                user = user,
                                                isSortByBirthday = state.sortType is BirthdaySortItem,
                                                onClick = navigateToUserProfile
                                            )
                                        }

                                    }

                                    is AlphabeticallySortItem -> {

                                        items(filteredUsersList) {
                                            UserItem(
                                                user = it,
                                                isSortByBirthday = state.sortType is BirthdaySortItem,
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