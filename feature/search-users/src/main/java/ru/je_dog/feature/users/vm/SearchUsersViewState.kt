package ru.je_dog.feature.users.vm

import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.list.sort.sort_items.AlphabeticallySortItem
import ru.je_dog.feature.users.model.UsersDepartmentTab
import ru.je_dog.feature.users.model.SearchUsersSortType

data class SearchUsersViewState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val sortType: ListSorterItem<UserPresentation> = AlphabeticallySortItem(),
    val searchInputFilter: String = "",
    val departmentFilter: UsersDepartmentTab = UsersDepartmentTab.All,
    val usersList: List<UserPresentation> = emptyList(),
    val filteredUsersList: List<UserPresentation> = emptyList()
)