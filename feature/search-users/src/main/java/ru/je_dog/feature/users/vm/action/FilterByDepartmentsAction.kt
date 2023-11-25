package ru.je_dog.feature.users.vm.action

import ru.je_dog.feature.users.model.DepartmentTab

data class FilterByDepartmentsAction(
    val department: DepartmentTab
): SearchUsersAction
