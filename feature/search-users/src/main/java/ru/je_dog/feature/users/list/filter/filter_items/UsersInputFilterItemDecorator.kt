package ru.je_dog.feature.users.list.filter.filter_items

import android.util.Log
import ru.je_dog.core.common.ext.isSubstringFor
import ru.je_dog.core.feature.common.list.filter.ListFilterItem
import ru.je_dog.core.feature.model.UserPresentation

class UsersInputFilterItemDecorator(
    private val filterItem: ListFilterItem<*, UserPresentation> = ListFilterItem.Empty(),
    override var filterValue: String = ""
): ListFilterItem<String, UserPresentation> {

    override fun execute(item: UserPresentation): Boolean {

        return if (filterValue.isNotEmpty()){

            if (
                filterValue isSubstringFor item.firstname
                ||
                filterValue isSubstringFor item.lastname
                ||
                filterValue isSubstringFor item.userTag
            ) {
                filterItem?.execute(item) ?: true
            }else {
                false
            }

        }else {
            true
        }

    }

    override fun updateFilterValue(filterValue: String) {
        this.filterValue = filterValue
    }

}