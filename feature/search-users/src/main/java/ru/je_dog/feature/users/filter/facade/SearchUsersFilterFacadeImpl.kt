package ru.je_dog.feature.users.filter.facade

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.je_dog.core.feature.common.list.filter.ListFilterItem
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.filter.SearchUsersFilter
import ru.je_dog.feature.users.filter.filter_items.UsersDepartmentFilterItemDecorator
import ru.je_dog.feature.users.filter.filter_items.UsersInputFilterItemDecorator
import ru.je_dog.feature.users.model.UsersDepartmentTab

class  SearchUsersFilterFacadeImpl: SearchUserFilterFacade {

    private val inputSearchFilterItem = UsersInputFilterItemDecorator()
    private val departmentTabFilterItem = UsersDepartmentFilterItemDecorator(inputSearchFilterItem)
    private val filterItem = departmentTabFilterItem
    private val searchUsersFilter = SearchUsersFilter(filterItem)

    //states
    override val filteredUsers = searchUsersFilter.filteredList
    private val _departmentTabFilter = MutableStateFlow(UsersDepartmentTab.All)
    private val _inputSearchFilter = MutableStateFlow("")
    override val departmentTabFilter: StateFlow<UsersDepartmentTab> = _departmentTabFilter
    override val inputSearchFilter: StateFlow<String> = _inputSearchFilter

    override fun updateDepartment(
        departmentTab: UsersDepartmentTab,
        list: List<UserPresentation>
    ) {
        departmentTabFilterItem.updateFilterValue(departmentTab)

        _departmentTabFilter.update {
            departmentTab
        }

        searchUsersFilter.setList(list)
    }

    override fun updateInputSearch(inputSearch: String,list: List<UserPresentation>) {
        inputSearchFilterItem.updateFilterValue(inputSearch)

        _inputSearchFilter.update {
            inputSearch
        }

        searchUsersFilter.setList(list)
    }

    override fun setList(newList: List<UserPresentation>) {
        searchUsersFilter.setList(newList)
    }

}