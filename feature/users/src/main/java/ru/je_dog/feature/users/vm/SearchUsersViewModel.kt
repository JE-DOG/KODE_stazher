package ru.je_dog.feature.users.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.core.common.ext.isSubstringFor
import ru.je_dog.core.feature.common.ext.sortByMonth
import ru.je_dog.core.feature.common.vm.Action
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.vm.action.ClickOnUserItemAction
import ru.je_dog.feature.users.vm.action.FilterByDepartmentsAction
import ru.je_dog.feature.users.vm.action.FilterByInputSearchAction
import ru.je_dog.feature.users.vm.action.RefreshAction
import ru.je_dog.feature.users.vm.action.SearchUsersAction
import ru.je_dog.feature.users.vm.action.SortByBirthdayAction
import ru.je_dog.feature.users.vm.action.SortByNameAction
import ru.je_dog.feature.users.vm.action.TryAgainLoadUsersAction
import ru.je_dog.users.use_cases.GetDynamicUsersUseCase
import ru.je_dog.users.use_cases.GetUsersUseCases
import ru.je_dog.users.use_cases.GetUsersWithErrorUseCase

class SearchUsersViewModel(
    private val getUsersUseCases: GetUsersUseCases,
    private val getDynamicUsersUseCase: GetDynamicUsersUseCase,
    private val getUsersWithErrorUseCase: GetUsersWithErrorUseCase,
    private val navController: NavController
): ViewModel() {

    private val _state = MutableStateFlow(SearchUsersViewState())
    val state: StateFlow<SearchUsersViewState> = _state

    init {

        loadUsers()

    }

    fun action(action: SearchUsersAction) {
        when (action) {

            is ClickOnUserItemAction -> {
                TODO("Implemented navigation to profile user")
            }

            is FilterByDepartmentsAction -> {
                filterUsers(
                    department = action.department
                )
            }

            is FilterByInputSearchAction -> {
                filterUsers(
                    inputSearch = action.inputSearch
                )
            }

            is RefreshAction -> {
                refreshUsers()
            }

            is SortByBirthdayAction -> {
                sortUser(action.SORT_NAME)
            }

            is SortByNameAction -> {
                sortUser(action.SORT_NAME)
            }

            is TryAgainLoadUsersAction -> {
                loadUsers()
            }

        }
    }

    private fun loadUsers(){

        viewModelScope.launch {

            getUsersUseCases.execute()
                .onStart {
                    _state.update {
                        SearchUsersViewState(
                            isLoading = true
                        )
                    }
                }
                .catch {
                    _state.update {
                        SearchUsersViewState(
                            isError = true
                        )
                    }
                }
                .collect { usersDomain ->

                    val users = usersDomain
                        .map { UserPresentation.fromDomain(it) }

                    _state.update {
                        SearchUsersViewState(
                            isLoading = false
                        )
                    }
                    updateUsers(users)

                }
        }

    }

    private fun refreshUsers() = viewModelScope.launch {

        getDynamicUsersUseCase.execute()
            .onStart {
                _state.update {
                    it.copy(
                        isRefreshing = true
                    )
                }
            }
            .catch {
                _state.update {
                    it.copy(
                        isRefreshing = null
                    )
                }
            }
            .collect { usersDomain ->

                val newUsers = usersDomain.map { UserPresentation.fromDomain(it) }
                val currentUsers = state.value.usersList

                updateUsers(currentUsers + newUsers)

            }

    }

    private fun updateUsers(
        newUsers: List<UserPresentation>
    ){

        val stateValue = state.value
        val updatedUsers = newUsers + stateValue.usersList

        sortUser(updatedUsers)

    }

    private fun filterUsers(
        users: List<UserPresentation>
    ): List<UserPresentation> {

        val stateValue = state.value
        val filteredList = users.toMutableList()

        for (user in filteredList){

            val inputSearch = stateValue.searchInputFilter
            if (inputSearch != null
                &&
                !(inputSearch isSubstringFor user.firstname
                    ||
                  inputSearch isSubstringFor user.lastname
                    ||
                  inputSearch isSubstringFor user.userTag)
                ){

                filteredList.remove(user)
                continue
            }

            val department = stateValue.departmentFilter
            if (department != null && department != user.department){
                filteredList.remove(user)
                continue
            }

        }

        return filteredList
    }

    private fun filterUsers(
        department: String? = state.value.departmentFilter,
        inputSearch: String? = state.value.searchInputFilter
    ) {

        val stateValue = state.value
        val filteredList = stateValue.usersList.toMutableList()

        for (user in filteredList){

            if (inputSearch != null
                &&
                !(inputSearch isSubstringFor user.firstname
                        ||
                        inputSearch isSubstringFor user.lastname
                        ||
                        inputSearch isSubstringFor user.userTag)
            ){

                filteredList.remove(user)
                continue
            }

            if (department != null && department != user.department){
                filteredList.remove(user)
                continue
            }

        }

        _state.update {
            it.copy(
                filteredUsersList = filteredList
            )
        }
    }

    private fun sortedListByAlphabetically(
        list: List<UserPresentation>
    ): List<UserPresentation> = list.sortedWith(
        compareBy(String.CASE_INSENSITIVE_ORDER){
            it.firstname
        }
    )

    private fun sortUser(
        sortType: String
    ) {

        val users = state.value.usersList

        when(sortType){

            SortByNameAction.SORT_NAME -> {

                val sortedUsers = sortedListByAlphabetically(
                    users
                )
                val filteredUsers = filterUsers(
                    sortedUsers
                )

                _state.update {
                    it.copy(
                        usersList = sortedUsers,
                        filteredUsersList = filteredUsers
                    )
                }

            }

            SortByBirthdayAction.SORT_NAME -> {

                val sortedUsers = users.sortByMonth()
                val filteredUsers = filterUsers(sortedUsers)

                _state.update {
                    it.copy(
                        filteredUsersList = filteredUsers,
                        usersList = sortedUsers
                    )
                }

            }

        }

    }

    private fun sortUser(
        users: List<UserPresentation>
    ) {

        val sortType = state.value.sortType

        when(sortType){

            SortByNameAction.SORT_NAME -> {

                val sortedUsers = sortedListByAlphabetically(
                    users
                )
                val filteredUsers = filterUsers(
                    sortedUsers
                )

                _state.update {
                    it.copy(
                        usersList = sortedUsers,
                        filteredUsersList = filteredUsers
                    )
                }

            }

            SortByBirthdayAction.SORT_NAME -> {

                val sortedUsers = users.sortByMonth()
                val filteredUsers = filterUsers(sortedUsers)

                _state.update {
                    it.copy(
                        filteredUsersList = filteredUsers,
                        usersList = sortedUsers
                    )
                }

            }

        }

    }

}