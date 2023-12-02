package ru.je_dog.core.feature.common.list.filter

import kotlinx.coroutines.flow.StateFlow

abstract class ListFilter<T> {

    protected abstract var listItemFilter: ListFilterItem<*, T>?

    abstract fun setList(newList: List<T>): List<T>

    abstract fun setListFilterItem(filterItem: ListFilterItem<*, T>?)

}