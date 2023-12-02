package ru.je_dog.core.feature.common.list.filter

interface ListFilterItem<FV,T> {

    var filterValue: FV

    fun execute(item: T): Boolean

    fun updateFilterValue(filterValue: FV)

}