package ru.je_dog.core.feature.common.list.filter

interface ListFilterItem<FV,T> {

    var filterValue: FV

    fun execute(item: T): Boolean

    fun updateFilterValue(filterValue: FV)

    class Empty<T>(
        expectOutput: Boolean = true
    ): ListFilterItem<Any,T>{

        override var filterValue: Any
            get() = TODO("Not yet implemented")
            set(value) {}

        override fun execute(item: T) = true

        override fun updateFilterValue(filterValue: Any) = Unit

    }

}