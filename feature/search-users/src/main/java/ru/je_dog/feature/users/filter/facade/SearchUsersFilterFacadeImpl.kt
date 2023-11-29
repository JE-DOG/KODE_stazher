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

class SearchUsersFilterFacadeImpl: SearchUserFilterFacade {

    private val departmentTabFilterItem = UsersDepartmentFilterItemDecorator()
    private val inputSearchFilterItem = UsersInputFilterItemDecorator(departmentTabFilterItem)
    private val filterItem = inputSearchFilterItem
    private val searchUsersFilter = SearchUsersFilter(filterItem)

    //states
    override val filteredUsers = searchUsersFilter.filteredList
    private val _departmentTabFilter = MutableStateFlow(UsersDepartmentTab.All)
    val departmentTabFilter: StateFlow<UsersDepartmentTab> = _departmentTabFilter
    private val _inputSearchFilter = MutableStateFlow("")
    val inputSearchFilter: StateFlow<String> = _inputSearchFilter

    override fun updateDepartment(departmentTab: UsersDepartmentTab) {
        departmentTabFilterItem.updateFilterValue(departmentTab)

        _departmentTabFilter.update {
            departmentTab
        }

        refreshList()
    }

    override fun updateInputSearch(inputSearch: String) {
        inputSearchFilterItem.updateFilterValue(inputSearch)

        _inputSearchFilter.update {
            inputSearch
        }

        refreshList()
    }

    override fun setList(newList: List<UserPresentation>) {
        searchUsersFilter.setList(newList)
    }

    private fun refreshList(){
        Log.d("FilterTag","Refresh list")
        searchUsersFilter.setList(filteredUsers.value)
    }

}