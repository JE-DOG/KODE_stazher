package ru.je_dog.core.feature.common.list.sort

interface ListSorterItem<T> {

    fun execute(list: List<T>): List<T>

}