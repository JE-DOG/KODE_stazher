package ru.je_dog.core.feature.common.ext

import android.icu.util.Calendar
import ru.je_dog.core.common.date.Date

inline fun<T> List<T>.sortByMonth(
    getDate: (T) -> Date
): List<T> {

    val sortedDates = this.toMutableList()
    var isSorted = false

    while (!isSorted){

        isSorted = true

        for (userIndex in 0 .. size - 2){

            val item = sortedDates[userIndex]
            val date = getDate(item)
            val nextItem = sortedDates[userIndex + 1]
            val nextDate = getDate(nextItem)

            if (date.isSmallerUntilMonth(nextDate) == false){
                isSorted = false
                sortedDates[userIndex] = nextItem
                sortedDates[userIndex + 1] = item
            }

        }

    }

    return sortedDates
}

inline fun<T> List<T>.getCurrentYearPoint(
    getDate: (T) -> Date
): Int{

    val calendar = Calendar.getInstance()
    val currentMonth = calendar.get(Calendar.MONTH) + 1
    val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    for (dateIndex in indices){

        val date = getDate(this[dateIndex])

        if (
            date.month > currentMonth
            ||
            date.month == currentMonth && date.day >= currentDayOfMonth
        ){
            return dateIndex
        }

    }

    return size
}