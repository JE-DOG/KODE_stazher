package ru.je_dog.feature.users.filter.filter_items

import android.util.Log
import ru.je_dog.core.feature.common.list.filter.ListFilterItem
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.model.UsersDepartmentTab

class UsersDepartmentFilterItemDecorator(
    private val filterItem: ListFilterItem<*, UserPresentation>? = null,
    override var filterValue: UsersDepartmentTab? = null
): ListFilterItem<UsersDepartmentTab, UserPresentation> {

    override fun execute(item: UserPresentation): Boolean {
        return if (filterValue != null){


            if (filterValue!!.tag == item.userTag) {

                filterItem?.execute(item) ?: true

            }else{
                false
            }

        }else {
            true
        }
    }

    override fun updateFilterValue(filterValue: UsersDepartmentTab) {
        Log.d("FiltUpdateTag",filterValue.text)
        this.filterValue = filterValue
    }

}