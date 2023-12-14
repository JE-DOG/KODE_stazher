package ru.je_dog.feature.users.model

import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.list.sort_items.AlphabeticallySortItem
import ru.je_dog.feature.users.list.sort_items.BirthdaySortItem

enum class SearchUsersSortType(
    val text: String,
    val sorterItem: ListSorterItem<UserPresentation>
) {

    Birthday(
        "По дню рождения",
        BirthdaySortItem {
            it.birthday
        }
    ),
    Alphabet(
        "По алфавиту",
        AlphabeticallySortItem()
    )

}