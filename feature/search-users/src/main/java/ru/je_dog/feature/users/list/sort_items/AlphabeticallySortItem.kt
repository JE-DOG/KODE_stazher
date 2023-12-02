package ru.je_dog.feature.users.list.sort_items

import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.model.UserPresentation

class AlphabeticallySortItem: ListSorterItem<UserPresentation> {

    override fun execute(list: List<UserPresentation>): List<UserPresentation> = list.sortedWith(
        compareBy(String.CASE_INSENSITIVE_ORDER){
            it.firstname
        }
    )

}