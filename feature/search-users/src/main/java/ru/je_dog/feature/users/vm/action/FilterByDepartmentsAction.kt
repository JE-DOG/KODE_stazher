package ru.je_dog.feature.users.vm.action

data class FilterByDepartmentsAction(
    val department: String?
): SearchUsersAction
