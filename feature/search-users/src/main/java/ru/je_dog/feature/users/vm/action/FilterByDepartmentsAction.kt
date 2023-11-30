package ru.je_dog.feature.users.vm.action

import ru.je_dog.feature.users.model.UsersDepartmentTab

data class FilterByDepartmentsAction(
    val department: UsersDepartmentTab
): SearchUsersAction
