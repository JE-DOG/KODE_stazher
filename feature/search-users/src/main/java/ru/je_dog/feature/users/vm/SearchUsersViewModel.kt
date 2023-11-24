package ru.je_dog.feature.users.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.gson.Gson
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
                val userJson = Gson().toJson(action.user)
                navController.navigate("userProfileRoute/$userJson")
            }

            is FilterByDepartmentsAction -> {
                setDepartmentFilter(
                    department = action.department
                )
            }

            is FilterByInputSearchAction -> {
                setInputSearchFilter(
                    action.inputSearch
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
                    Log.d("SearchUsersScreenState","Load users start")
                    _state.update {
                        SearchUsersViewState(
                            isLoading = true
                        )
                    }
                }
                .catch {
                    Log.e("SearchUsersScreenState","${it.message}")
                    _state.update {
                        SearchUsersViewState(
                            isError = true
                        )
                    }
                }
                .collect { usersDomain ->

                    Log.d("SearchUsersScreenState", "$usersDomain")

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
            .collect { usersDomain ->

                val newUsers = usersDomain.map { UserPresentation.fromDomain(it) }

                updateUsers(newUsers)

                _state.update {
                    it.copy(
                        isRefreshing = false
                    )
                }
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
        var filteredList = mutableListOf<UserPresentation>()

        val inputSearch = stateValue.searchInputFilter
        val department = stateValue.departmentFilter

        if (department == null && inputSearch == null)
            return users

        for (user in users){

            if (department != null){

                if (department == user.department) {


                    if (inputSearch != null) {
                        if (
                            inputSearch isSubstringFor user.firstname
                            ||
                            inputSearch isSubstringFor user.lastname
                            ||
                            inputSearch isSubstringFor user.userTag
                        ) {
                            filteredList.add(user)
                        }

                    } else {
                        filteredList.add(user)
                    }

                }

            }else {

                if (inputSearch != null) {
                    if (
                        inputSearch isSubstringFor user.firstname
                        ||
                        inputSearch isSubstringFor user.lastname
                        ||
                        inputSearch isSubstringFor user.userTag
                    ) {
                        filteredList.add(user)
                    }

                }else {

                    filteredList = stateValue.usersList.toMutableList()
                    break

                }

            }

        }

        return filteredList
    }

    private fun setDepartmentFilter(
        department: String?
    ) {

        val stateValue = state.value
        val inputSearch = stateValue.searchInputFilter
        var filteredList = mutableListOf<UserPresentation>()

        for (user in stateValue.usersList){

            if (department != null){

                if (department == user.department) {


                    if (inputSearch != null) {
                        if (
                            inputSearch isSubstringFor user.firstname
                            ||
                            inputSearch isSubstringFor user.lastname
                            ||
                            inputSearch isSubstringFor user.userTag
                        ) {
                            filteredList.add(user)
                        }

                    } else {
                        filteredList.add(user)
                    }

                }

            }else {

                if (inputSearch != null) {
                    if (
                        inputSearch isSubstringFor user.firstname
                        ||
                        inputSearch isSubstringFor user.lastname
                        ||
                        inputSearch isSubstringFor user.userTag
                    ) {
                        filteredList.add(user)
                    }

                }else {

                    filteredList = stateValue.usersList.toMutableList()
                    break

                }

            }

        }

        _state.update {
            it.copy(
                departmentFilter = department,
                filteredUsersList = filteredList
            )
        }
    }

    private fun setInputSearchFilter(
        inputSearch: String?
    ) {

        val stateValue = state.value
        val department = stateValue.departmentFilter
        var filteredList = mutableListOf<UserPresentation>()

        for (user in stateValue.usersList){

            if (department != null){

                if (department == user.department) {


                    if (inputSearch != null) {
                        if (
                            inputSearch isSubstringFor user.firstname
                            ||
                            inputSearch isSubstringFor user.lastname
                            ||
                            inputSearch isSubstringFor user.userTag
                        ) {
                            filteredList.add(user)
                        }

                    } else {
                        filteredList.add(user)
                    }

                }

            }else {

                if (inputSearch != null) {
                    if (
                        inputSearch isSubstringFor user.firstname
                        ||
                        inputSearch isSubstringFor user.lastname
                        ||
                        inputSearch isSubstringFor user.userTag
                    ) {
                        filteredList.add(user)
                    }

                }else {

                    filteredList = stateValue.usersList.toMutableList()
                    break

                }

            }

        }

        _state.update {
            it.copy(
                searchInputFilter = inputSearch,
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
                        filteredUsersList = filteredUsers,
                        sortType = sortType
                    )
                }

            }

            SortByBirthdayAction.SORT_NAME -> {

                val sortedUsers = users.sortByMonth()
                val filteredUsers = filterUsers(sortedUsers)

                _state.update {
                    it.copy(
                        filteredUsersList = filteredUsers,
                        usersList = sortedUsers,
                        sortType = sortType
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