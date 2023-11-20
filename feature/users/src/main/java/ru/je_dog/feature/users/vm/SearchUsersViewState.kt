package ru.je_dog.feature.users.vm

import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.vm.action.SortByNameAction

data class SearchUsersViewState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean? = false,
    val sortType: String = SortByNameAction.SORT_NAME,
    val searchInputFilter: String? = null,
    val departmentFilter: String? = null,
    val usersList: List<UserPresentation> = emptyList(),
    val filteredUsersList: List<UserPresentation>? = null
)