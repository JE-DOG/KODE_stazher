package ru.je_dog.feature.users.list

import kotlinx.coroutines.flow.StateFlow
import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.common.list.sort.ListSorterState
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.list.filter.UsersFilterState
import ru.je_dog.feature.users.model.UsersDepartmentTab

typealias UsersSorterState = ListSorterState<UserPresentation>

interface UsersListFacade {

    val filterState: StateFlow<UsersFilterState>
    val sorterState: StateFlow<UsersSorterState>

    fun updateDepartment(department: UsersDepartmentTab)
    fun updateInputSearch(inputSearch: String)

    fun updateSortItem(sorterItem: ListSorterItem<UserPresentation>)

    fun setList(users: List<UserPresentation>)

    fun addNewList(newUsers: List<UserPresentation>)

}