package ru.je_dog.feature.users.filter.facade

import kotlinx.coroutines.flow.StateFlow
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.model.UsersDepartmentTab

interface SearchUserFilterFacade {

    val filteredUsers: StateFlow<List<UserPresentation>>

    fun updateDepartment(departmentTab: UsersDepartmentTab)

    fun updateInputSearch(inputSearch: String)

    fun setList(newList: List<UserPresentation>)

}