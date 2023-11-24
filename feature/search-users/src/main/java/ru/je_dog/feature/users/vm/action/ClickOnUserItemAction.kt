package ru.je_dog.feature.users.vm.action

import ru.je_dog.core.feature.model.UserPresentation

data class ClickOnUserItemAction(
    val user: UserPresentation
): SearchUsersAction
