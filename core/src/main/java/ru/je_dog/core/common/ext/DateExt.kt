package ru.je_dog.core.common.ext

import ru.je_dog.core.common.date.Date
import java.util.Calendar

fun List<Date>.sortByMonth(): List<Date> {

    val sortedDates = this.toMutableList()
    var isSorted = false

    while (!isSorted){

        isSorted = true

        for (dateIndex in 0 .. size - 2){

            val date = sortedDates[dateIndex]
            val nextDate = sortedDates[dateIndex + 1]

            if (date.isSmallerUntilMonth(nextDate) == true){
                isSorted = false
                sortedDates[dateIndex] = nextDate
                sortedDates[dateIndex + 1] = date
            }

        }

    }

    return sortedDates
}

fun List<Date>.divedByBirthday(
    currentDate: Date
): Pair<List<Date>,List<Date>>{

    val currentYear = mutableListOf<Date>()

    forEach {

        if (
            it.isSmallerUntilMonth(currentDate) == true
        )
            currentYear.add(it)

    }
    val nextYear = drop(currentYear.size)

    return Pair(
        currentYear,
        nextYear
    )
}