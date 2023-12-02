package ru.je_dog.feature.users.list.sort_items

import android.icu.util.Calendar
import ru.je_dog.core.feature.common.ext.sortByMonth
import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.model.UserPresentation

class BirthdaySortItem: ListSorterItem<UserPresentation> {

    var currentYearPoint = 0
        private set

    override fun execute(list: List<UserPresentation>): List<UserPresentation> {
        val sortByMonth = list.sortByMonth()

        setYearPoint(sortByMonth)

        return sortByMonth
    }

    fun setYearPoint(users: List<UserPresentation>){

        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        for (userIndex in users.indices){

            val user = users[userIndex]

            if (
                user.birthday.month > currentMonth
                ||
                user.birthday.month == currentMonth && user.birthday.day >= currentDayOfMonth
            ){
                currentYearPoint = userIndex
                return
            }

        }

        currentYearPoint = users.size

    }

}