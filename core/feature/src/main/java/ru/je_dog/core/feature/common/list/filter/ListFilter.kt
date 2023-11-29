package ru.je_dog.core.feature.common.list.filter

import kotlinx.coroutines.flow.StateFlow

interface ListFilter<T> {

    val filteredList: StateFlow<List<T>>
    var listItemFilter: ListFilterItem<*, T>?

    fun setList(newList: List<T>)

    fun setListFilterItem(filterItem: ListFilterItem<*, T>?)

}