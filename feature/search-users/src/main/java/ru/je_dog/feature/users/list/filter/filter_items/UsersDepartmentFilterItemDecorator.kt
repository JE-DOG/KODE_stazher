package ru.je_dog.feature.users.list.filter.filter_items

import android.util.Log
import ru.je_dog.core.feature.common.list.filter.ListFilterItem
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.model.UsersDepartmentTab

class UsersDepartmentFilterItemDecorator(
    private val filterItem: ListFilterItem<*, UserPresentation> = ListFilterItem.Empty(),
    override var filterValue: UsersDepartmentTab = UsersDepartmentTab.All
): ListFilterItem<UsersDepartmentTab, UserPresentation> {

    override fun execute(item: UserPresentation): Boolean {
        return if (filterValue != UsersDepartmentTab.All){

            if (filterValue.tag == item.department) {

                filterItem.execute(item)

            }else{

                false
            }

        }else {
            true
        }
    }

    override fun updateFilterValue(filterValue: UsersDepartmentTab) {
        this.filterValue = filterValue
    }

}