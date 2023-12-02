package ru.je_dog.feature.users.vm

import ru.je_dog.core.feature.common.list.sort.ListSorterState
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.list.filter.UsersFilterState
import ru.je_dog.feature.users.model.SearchUsersSortType

data class SearchUsersViewState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val sorterState: ListSorterState<UserPresentation> = ListSorterState(sorterItem = SearchUsersSortType.Alphabet.sorterItem),
    val filterState: UsersFilterState = UsersFilterState()
)