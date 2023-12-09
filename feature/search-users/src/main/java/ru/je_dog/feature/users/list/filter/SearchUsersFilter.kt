package ru.je_dog.feature.users.list.filter

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.je_dog.core.feature.common.list.filter.ListFilter
import ru.je_dog.core.feature.common.list.filter.ListFilterItem
import ru.je_dog.core.feature.model.UserPresentation

class SearchUsersFilter(
    override var listItemFilter: ListFilterItem<*, UserPresentation> = ListFilterItem.Empty()
): ListFilter<UserPresentation>() {

    override fun setList(newList: List<UserPresentation>): List<UserPresentation> {
        val newFilteredList = mutableListOf<UserPresentation>()

        newList.forEach { user ->

            if (listItemFilter.execute(user)){
                newFilteredList.add(user)
            }

        }

        return newFilteredList
    }

    override fun setListFilterItem(filterItem: ListFilterItem<*, UserPresentation>) {
        listItemFilter = filterItem
    }

}