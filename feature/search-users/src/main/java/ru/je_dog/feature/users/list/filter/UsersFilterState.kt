package ru.je_dog.feature.users.list.filter

import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.model.UsersDepartmentTab

data class UsersFilterState(
    val filteredList: List<UserPresentation> = emptyList(),
    val departmentTab: UsersDepartmentTab = UsersDepartmentTab.All,
    val inputSearch: String = ""
)
