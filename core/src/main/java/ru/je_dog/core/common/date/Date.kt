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

    fun monthName(): String = when(month){

        1 -> "январь"
        2 -> "февраль"
        3 -> "март"
        4 -> "апрель"
        5 -> "май"
        6 -> "июнь"
        7 -> "июль"
        8 -> "август"
        9 -> "сентябрь"
        10 -> "октябрь"
        11-> "ноябрь"
        12 -> "декабрь"

        else -> throw IllegalArgumentException("Unknown month")
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


