package ru.je_dog.feature.users.filter.filter_items

import ru.je_dog.core.common.ext.isSubstringFor
import ru.je_dog.core.feature.common.list.filter.ListFilterItem
import ru.je_dog.core.feature.model.UserPresentation

class UsersInputFilterItemDecorator(
    private val filterItem: ListFilterItem<*, UserPresentation>,
    override var filterValue: String? = null
): ListFilterItem<String, UserPresentation> {

    override fun execute(item: UserPresentation): Boolean {

        return if (filterValue != null){

            return filterValue!! isSubstringFor item.firstname
                    ||
                    filterValue!! isSubstringFor item.lastname
                    ||
                    filterValue!! isSubstringFor item.userTag

        }else {
            true
        }

    }

    override fun updateFilterValue(filterValue: String) {
        this.filterValue = filterValue
    }

}