package ru.je_dog.feature.users.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.core.common.ext.isSubstringFor
import ru.je_dog.core.data.common.exception.FailedRequestException
import ru.je_dog.core.feature.common.ext.sortByMonth
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.filter.facade.SearchUserFilterFacade
import ru.je_dog.feature.users.filter.facade.SearchUsersFilterFacadeImpl
import ru.je_dog.feature.users.model.UsersDepartmentTab
import ru.je_dog.feature.users.model.SearchUsersSortType
import ru.je_dog.feature.users.vm.action.FilterByDepartmentsAction
import ru.je_dog.feature.users.vm.action.FilterByInputSearchAction
import ru.je_dog.feature.users.vm.action.RefreshAction
import ru.je_dog.feature.users.vm.action.SearchUsersAction
import ru.je_dog.feature.users.vm.action.SortByAction
import ru.je_dog.feature.users.vm.action.LoadUsersAction
import ru.je_dog.users.use_cases.GetDynamicUsersUseCase
import ru.je_dog.users.use_cases.GetUsersUseCases
import ru.je_dog.users.use_cases.GetUsersWithErrorUseCase
import kotlin.random.Random

class SearchUsersViewModel(
    private val getUsersUseCases: GetUsersUseCases,
    private val getDynamicUsersUseCase: GetDynamicUsersUseCase,
    private val getUsersWithErrorUseCase: GetUsersWithErrorUseCase
): ViewModel() {

    private val _state = MutableStateFlow(SearchUsersViewState())
    val state: StateFlow<SearchUsersViewState> = _state

    private val searchUsersFilter: SearchUserFilterFacade = SearchUsersFilterFacadeImpl()

    init {

        initUsersFilter()

        loadUsers()

    }

    private fun initUsersFilter() = viewModelScope.launch {

        with(searchUsersFilter){

            launch {
                filteredUsers.collect { filteredUsers ->
                    Log.d("FilterTago","Update filtered list")
                    Log.d("FilterTago",filteredUsers.toString())

                    _state.update { currentState ->
                        currentState.copy(
                            filteredUsersList = filteredUsers
                        )
                    }

                }
            }
            launch {
                inputSearchFilter.collect { inputSearchFilter ->
                    Log.d("FilterTago","Update filtered list")
                    Log.d("FilterTago",inputSearchFilter)
                    _state.update {
                        it.copy(
                            searchInputFilter = inputSearchFilter
                        )
                    }
                }
            }
            launch {
                departmentTabFilter.collect { departmentTab ->
                    _state.update {
                        it.copy(
                            departmentFilter = departmentTab
                        )
                    }
                }
            }

        }

    }

    fun action(action: SearchUsersAction) {
        when (action) {

            is FilterByDepartmentsAction -> {
                searchUsersFilter.updateDepartment(
                    action.department,
                    state.value.usersList
                )
            }

            is FilterByInputSearchAction -> {
                searchUsersFilter.updateInputSearch(
                    action.inputSearch,
                    state.value.usersList
                )
            }

            is RefreshAction -> {
                refreshUsers()
            }

            is SortByAction -> {
                sortUsers(action.sortType)
            }

            is LoadUsersAction -> {
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

        val random = Random.nextBoolean()

        if (random){

            getDynamicUsersUseCase.execute()
                .onStart {
                    _state.update {
                        it.copy(
                            isRefreshing = true
                        )
                    }
                }
                .catch { throwable ->
                    if (throwable is FailedRequestException){
                        throwable.printStackTrace()
                        Log.e("ErrorTag","FailedRequestException\nError code: ${throwable.errorCode}")
                    }

                    _state.update {
                        SearchUsersViewState(isError = true)
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

        }else {

            getUsersWithErrorUseCase.execute()
                .catch {  throwable ->
                    if (throwable is FailedRequestException){
                        throwable.printStackTrace()
                        Log.e("ErrorTag","FailedRequestException\nError code: ${throwable.errorCode}")
                    }

                    _state.update {
                        SearchUsersViewState(isError = true)
                    }
                }
                .collect()

        }

    }

    private fun updateUsers(
        newUsers: List<UserPresentation>
    ){

        val stateValue = state.value
        val updatedUsers = newUsers + stateValue.usersList

        sortUsers(updatedUsers)

    }

    private fun sortedListByAlphabetically(
        list: List<UserPresentation>
    ): List<UserPresentation> = list.sortedWith(
        compareBy(String.CASE_INSENSITIVE_ORDER){
            it.firstname
        }
    )

    private fun sortUsers(
        sortType: SearchUsersSortType
    ) {

        val users = state.value.usersList

        when(sortType){

            SearchUsersSortType.Alphabet -> {

                val sortedUsers = sortedListByAlphabetically(
                    users
                )
                searchUsersFilter.setList(sortedUsers)

                _state.update {
                    it.copy(
                        usersList = sortedUsers,
                        sortType = sortType
                    )
                }

            }

            SearchUsersSortType.Birthday -> {

                val sortedUsers = users.sortByMonth()
                searchUsersFilter.setList(sortedUsers)

                _state.update {
                    it.copy(
                        usersList = sortedUsers,
                        sortType = sortType
                    )
                }

            }

        }

    }

    private fun sortUsers(
        users: List<UserPresentation>
    ) {

        val sortType = state.value.sortType

        when(sortType){

            SearchUsersSortType.Alphabet -> {

                val sortedUsers = sortedListByAlphabetically(
                    users
                )
                searchUsersFilter.setList(sortedUsers)

                _state.update {
                    it.copy(
                        usersList = sortedUsers
                    )
                }

            }

            SearchUsersSortType.Birthday -> {

                val sortedUsers = users.sortByMonth()
                searchUsersFilter.setList(sortedUsers)

                _state.update {
                    it.copy(
                        usersList = sortedUsers
                    )
                }

            }

        }

    }

}