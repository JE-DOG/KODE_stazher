package ru.je_dog.feature.users.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.core.data.common.exception.FailedRequestException
import ru.je_dog.core.feature.common.list.sort.ListSorter
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.list.filter.facade.SearchUserFilterFacade
import ru.je_dog.feature.users.list.filter.facade.SearchUsersFilterFacadeImpl
import ru.je_dog.feature.users.list.sort.UsersListSorter
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

    private val usersFilter: SearchUserFilterFacade = SearchUsersFilterFacadeImpl()
    private val usersSorter: ListSorter<UserPresentation> = UsersListSorter(state.value.sortType)

    init {

        initUsersFilter()
        initUsersSorter()

        loadUsers()

    }

    private fun initUsersSorter() = viewModelScope.launch {

        usersSorter.listSorterState.collect { listSorterState ->

            usersFilter.setList(listSorterState.list)

            _state.update { currentState ->
                currentState.copy(
                    usersList = listSorterState.list,
                    sortType = listSorterState.sorterItem
                )
            }
        }

    }

    private fun initUsersFilter() = viewModelScope.launch {

        with(usersFilter){

            launch {
                filteredUsers.collect { filteredUsers ->


                    _state.update { currentState ->
                        currentState.copy(
                            filteredUsersList = filteredUsers
                        )
                    }
                }
            }
            launch {
                inputSearchFilter.collect { inputSearchFilter ->
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
                usersFilter.updateDepartment(
                    action.department,
                    state.value.usersList
                )
            }

            is FilterByInputSearchAction -> {
                usersFilter.updateInputSearch(
                    action.inputSearch,
                    state.value.usersList
                )
            }

            is RefreshAction -> {
                refreshUsers()
            }

            is SortByAction -> {
                usersSorter.updateSorterItem(action.sortType.sorterItem)
            }

            is LoadUsersAction -> {
                loadUsers()
            }

        }
    }

    private fun loadUsers() = viewModelScope.launch {

            getUsersUseCases.execute()
                .onStart {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                .catch {
                    _state.update {
                        it.copy(
                            isError = true
                        )
                    }
                }
                .collect { usersDomain ->
                    val users = usersDomain
                        .map { UserPresentation.fromDomain(it) }

                    usersSorter.setList(users)

                    _state.update {
                        it.copy(
                            isLoading = false
                        )
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
                        it.copy(isError = true)
                    }

                }
                .collect { usersDomain ->

                    val newUsers = usersDomain.map { UserPresentation.fromDomain(it) }
                    val updatedUsers = newUsers + state.value.usersList
                    usersSorter.setList(updatedUsers)

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
                        it.copy(isError = true)
                    }
                }
                .collect()

        }

    }

}