package ru.je_dog.core.feature.common.list.sort

import kotlinx.coroutines.flow.StateFlow

interface ListSorter<T> {

    val listSorterState: StateFlow<ListSorterState<T>>

    fun setList(list: List<T>)

    fun updateSorterItem(newSorterItem: ListSorterItem<T>)

}