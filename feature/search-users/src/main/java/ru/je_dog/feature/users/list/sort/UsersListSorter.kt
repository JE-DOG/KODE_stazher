package ru.je_dog.feature.users.list.sort

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.je_dog.core.feature.common.list.sort.ListSorter
import ru.je_dog.core.feature.common.list.sort.ListSorterItem
import ru.je_dog.core.feature.common.list.sort.ListSorterState
import ru.je_dog.core.feature.model.UserPresentation

class UsersListSorter(
    private var listItemSorter: ListSorterItem<UserPresentation>
): ListSorter<UserPresentation> {


    private val _sortedList = MutableStateFlow(
        ListSorterState(
            emptyList(),
            listItemSorter
        )
    )
    override val listSorterState: StateFlow<ListSorterState<UserPresentation>> = _sortedList

    override fun setList(list: List<UserPresentation>) {
        _sortedList.update {
            it.copy(
                listItemSorter.execute(
                    list
                )
            )
        }
    }

    override fun updateSorterItem(newSorterItem: ListSorterItem<UserPresentation>) {
        _sortedList.update { currentState ->
            ListSorterState(
                newSorterItem.execute(currentState.list),
                newSorterItem
            )
        }
    }

}