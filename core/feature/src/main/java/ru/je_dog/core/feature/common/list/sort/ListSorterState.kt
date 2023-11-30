package ru.je_dog.core.feature.common.list.sort

data class ListSorterState<T>(
    val list: List<T>,
    val sorterItem: ListSorterItem<T>
)
