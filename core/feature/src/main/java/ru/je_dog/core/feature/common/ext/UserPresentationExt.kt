package ru.je_dog.core.feature.common.ext

import ru.je_dog.core.common.date.Date
import ru.je_dog.core.feature.model.UserPresentation

fun List<UserPresentation>.sortByMonth(): List<UserPresentation> {

    val sortedDates = this.toMutableList()
    var isSorted = false

    while (!isSorted){

        isSorted = true

        for (userIndex in 0 .. size - 2){

            val user = sortedDates[userIndex]
            val nextUser = sortedDates[userIndex + 1]

            if (user.birthday.isSmallerUntilMonth(nextUser.birthday) == false){
                isSorted = false
                sortedDates[userIndex] = nextUser
                sortedDates[userIndex + 1] = user
            }

        }

    }

    return sortedDates
}