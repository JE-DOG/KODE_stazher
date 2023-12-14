package ru.je_dog.feature.users.list

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.list.filter.SearchUsersFilter
import ru.je_dog.feature.users.list.filter.UsersFilterState
import ru.je_dog.feature.users.list.filter.filter_items.UsersDepartmentFilterItemDecorator
import ru.je_dog.feature.users.list.filter.filter_items.UsersInputFilterItemDecorator
import ru.je_dog.feature.users.list.sort_items.BirthdaySortItem
import ru.je_dog.feature.users.model.SearchUsersSortType
import ru.je_dog.feature.users.model.UsersDepartmentTab

class UsersListFacadeImpl: UsersListFacade {

    //region filter items
    private val inputSearchFilterItem = UsersInputFilterItemDecorator()
    private val departmentTabFilterItem = UsersDepartmentFilterItemDecorator(inputSearchFilterItem)
    private val filterItem = departmentTabFilterItem
    private val searchUsersFilter = SearchUsersFilter(filterItem)
    //endregion

    private val _filterState = MutableStateFlow(UsersFilterState())
    override val filterState: StateFlow<UsersFilterState> = _filterState

    private val _sorterState = MutableStateFlow(UsersSorterState(
        sorterItem = SearchUsersSortType.Alphabet.sorterItem
    ))
    override val sorterState: StateFlow<UsersSorterState> = _sorterState

    override fun setList(users: List<UserPresentation>) {

        val newSortedList = sorterState.value.sorterItem.execute(users)
        _sorterState.update { sorterState ->
            sorterState.copy(
                list = newSortedList
            )
        }

        updateFilteredList(newSortedList)

    }

    override fun updateDepartment(department: UsersDepartmentTab) {

        departmentTabFilterItem.updateFilterValue(department)
        updateFilteredList(sorterState.value.list)

    }

    override fun updateInputSearch(inputSearch: String) {

        inputSearchFilterItem.updateFilterValue(inputSearch)
        updateFilteredList(sorterState.value.list)

    }

    override fun updateSortItem(sorterItem: ListSorterItem<UserPresentation>) {

        val newSortedList = sorterItem.execute(sorterState.value.list)
        _sorterState.update { sorterState ->
            sorterState.copy(
                list = newSortedList,
                sorterItem = sorterItem
            )
        }

        updateFilteredList(newSortedList)

    }

    override fun addNewList(newUsers: List<UserPresentation>) {

        val users = sorterState.value.list + newUsers
        setList(users)

    }

    private fun updateFilteredList(sortedList: List<UserPresentation>){

        val newFilteredList = searchUsersFilter.setList(sortedList)
        val sortItem = sorterState.value.sorterItem

        if (sortItem is BirthdaySortItem){
            sortItem.updateCurrentYearPoint(newFilteredList)
        }

        _filterState.update { filterState ->
            filterState.copy(
                filteredList = newFilteredList,
                departmentTab = departmentTabFilterItem.filterValue,
                inputSearch = inputSearchFilterItem.filterValue
            )
        }

    }

}