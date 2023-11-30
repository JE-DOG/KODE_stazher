package ru.je_dog.feature.users.vm.action

import ru.je_dog.feature.users.model.SearchUsersSortType

data class SortByAction(
    val sortType: SearchUsersSortType
): SearchUsersAction
