package ru.je_dog.core.feature.common.list.filter

interface ListFilterItem<FV,T> {

    var filterValue: FV

    fun execute(item: T): Boolean

    fun updateFilterValue(filterValue: FV)

    class Empty<T>(
        var expectOutput: (T) -> Boolean = { true }
    ): ListFilterItem<Boolean,T>{

        override var filterValue: Boolean
            get() = TODO("Not yet implemented")
            set(value) {}

        override fun execute(item: T) = expectOutput(item)

        override fun updateFilterValue(filterValue: Boolean){
            expectOutput = { filterValue }
        }

    }

}