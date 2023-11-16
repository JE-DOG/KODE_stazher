package ru.je_dog.core.common.date

class Date private constructor(
    val year: Int,
    val month: Int,
    val day: Int
){

    fun isSmallerUntilYear(date: Date): Boolean? {

        if (
            year == date.year
            &&
            month == date.month
            &&
            day == date.day
        )
            return null

        return if (year < date.year)
            true
        else if (
            year == date.year
            &&
            month < date.month
        )
            true
        else year == date.year
                &&
                month == date.month
                &&
                day < date.day

    }

    fun isSmallerUntilMonth(date: Date): Boolean? {
        if (
            month == date.month
            &&
            day == date.day
        )
            return null

        return if (month < date.month)
            true
        else
            month == date.month
                   &&
            day < date.day
    }

    companion object {

        fun create(
            date: String
        ): Date {

            val year = date.take(4).toInt()
            val month = "${date[5]}${date[6]}".toInt()
            val day = date.takeLast(2).toInt()

            return Date(
                year,
                month,
                day
            )
        }

    }

}


