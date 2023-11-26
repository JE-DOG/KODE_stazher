package ru.je_dog.feature.users.vm

import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.model.SearchUsersDepartmentTab
import ru.je_dog.feature.users.model.SearchUsersSortType

data class SearchUsersViewState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val sortType: SearchUsersSortType = SearchUsersSortType.Alphabet,
    val searchInputFilter: String? = null,
    val departmentFilter: SearchUsersDepartmentTab = SearchUsersDepartmentTab.All,
    val usersList: List<UserPresentation> = emptyList(),
    val filteredUsersList: List<UserPresentation> = emptyList()
)