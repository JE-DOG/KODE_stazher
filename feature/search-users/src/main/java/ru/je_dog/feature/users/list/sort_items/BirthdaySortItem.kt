package ru.je_dog.feature.users.list.sort_items

import ru.je_dog.core.common.date.Date
import ru.je_dog.core.feature.common.ext.getCurrentYearPoint
import ru.je_dog.core.feature.common.ext.sortByMonth
import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.model.UserPresentation

class BirthdaySortItem<T>(
    private val getDate: (T) -> Date
): ListSorterItem<T> {

    var currentYearPoint = 0
        private set

    override fun execute(list: List<T>): List<T> {
        val sortByMonth = list.sortByMonth(getDate)
        currentYearPoint = sortByMonth.getCurrentYearPoint(getDate)

        return sortByMonth
    }

    fun updateCurrentYearPoint(
        list: List<T>
    ){
        currentYearPoint = list.getCurrentYearPoint(getDate)
    }

}