package ru.je_dog.feature.users.list.filter.facade

import kotlinx.coroutines.flow.StateFlow
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.model.UsersDepartmentTab

interface SearchUserFilterFacade {

    val filteredUsers: StateFlow<List<UserPresentation>>
    val inputSearchFilter: StateFlow<String>
    val departmentTabFilter: StateFlow<UsersDepartmentTab>

    fun updateDepartment(departmentTab: UsersDepartmentTab,list: List<UserPresentation>)

    fun updateInputSearch(inputSearch: String,list: List<UserPresentation>)

    fun setList(newList: List<UserPresentation>)

}