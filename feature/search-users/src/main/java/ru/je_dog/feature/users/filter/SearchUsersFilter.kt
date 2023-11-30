package ru.je_dog.feature.users.filter

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.je_dog.core.feature.common.list.filter.ListFilter
import ru.je_dog.core.feature.common.list.filter.ListFilterItem
import ru.je_dog.core.feature.model.UserPresentation

class SearchUsersFilter(
    override var listItemFilter: ListFilterItem<*, UserPresentation>? = null
): ListFilter<UserPresentation> {

    private val _filteredList = MutableStateFlow(emptyList<UserPresentation>())
    override val filteredList: StateFlow<List<UserPresentation>> = _filteredList

    override fun setList(newList: List<UserPresentation>) {
        if (listItemFilter != null){
            val newFilteredList = mutableListOf<UserPresentation>()

            newList.forEach { user ->

                if (listItemFilter!!.execute(user)){
                    newFilteredList.add(user)
                }

            }

            _filteredList.update {
                newFilteredList
            }

        }else {
            _filteredList.update {
                newList
            }
        }
    }

    override fun setListFilterItem(filterItem: ListFilterItem<*, UserPresentation>?) {
        listItemFilter = filterItem
    }

}