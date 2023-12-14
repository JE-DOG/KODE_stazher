package ru.je_dog.feature.users.list.sort_items

import ru.je_dog.core.feature.common.ext.getCurrentYearPoint
import ru.je_dog.core.feature.common.ext.sortByMonth
import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.model.UserPresentation

class BirthdaySortItem: ListSorterItem<UserPresentation> {

    var currentYearPoint = 0
        private set

    override fun execute(list: List<UserPresentation>): List<UserPresentation> {
        val sortByMonth = list.sortByMonth {
            it.birthday
        }
        currentYearPoint = sortByMonth.getCurrentYearPoint { it.birthday }

        return sortByMonth
    }

}