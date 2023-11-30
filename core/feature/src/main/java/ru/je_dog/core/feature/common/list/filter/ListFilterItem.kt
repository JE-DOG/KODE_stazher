package ru.je_dog.core.feature.common.list.filter

interface ListFilterItem<FV,T> {

    var filterValue: FV?

    fun execute(item: T): Boolean

    fun updateFilterValue(filterValue: FV)

    class Empty<FV,T>: ListFilterItem<FV, T> {

        override var filterValue: FV? = null

        override fun execute(item: T): Boolean = true

        override fun updateFilterValue(filterValue: FV) = Unit

    }

}