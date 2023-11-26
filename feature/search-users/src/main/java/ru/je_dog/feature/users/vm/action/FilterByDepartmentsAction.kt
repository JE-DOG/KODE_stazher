package ru.je_dog.feature.users.vm.action

import ru.je_dog.feature.users.model.SearchUsersDepartmentTab

data class FilterByDepartmentsAction(
    val department: SearchUsersDepartmentTab
): SearchUsersAction
