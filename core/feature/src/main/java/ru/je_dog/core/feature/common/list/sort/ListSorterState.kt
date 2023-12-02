package ru.je_dog.core.feature.common.list.sort

data class ListSorterState<T>(
    val list: List<T> = emptyList(),
    val sorterItem: ListSorterItem<T>
)
