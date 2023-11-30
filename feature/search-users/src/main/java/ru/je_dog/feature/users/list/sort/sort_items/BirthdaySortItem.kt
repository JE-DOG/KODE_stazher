package ru.je_dog.feature.users.list.sort.sort_items

import ru.je_dog.core.feature.common.ext.sortByMonth
import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.model.UserPresentation

class BirthdaySortItem: ListSorterItem<UserPresentation> {

    override fun execute(list: List<UserPresentation>): List<UserPresentation> = list.sortByMonth()

}