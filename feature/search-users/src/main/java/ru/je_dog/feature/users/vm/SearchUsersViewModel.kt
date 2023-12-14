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
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.list.UsersListFacade
import ru.je_dog.feature.users.list.UsersListFacadeImpl
import ru.je_dog.feature.users.list.sort_items.BirthdaySortItem
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

    private val usersListFacade: UsersListFacade = UsersListFacadeImpl()

    init {

        initUsersListFacade()

        loadUsers()

    }

    private fun initUsersListFacade() = viewModelScope.launch {
        with(usersListFacade) {

            launch {
                filterState.collect { newFilterState ->
                    _state.update { currentState ->
                        currentState.copy(
                            filterState = newFilterState
                        )
                    }
                }
            }

            launch {
                sorterState.collect { newSorterState ->
                    _state.update { currentState ->
                        currentState.copy(
                            sorterState = newSorterState
                        )
                    }
                }
            }

        }
    }

    fun action(action: SearchUsersAction) {
        when (action) {

            is FilterByDepartmentsAction -> {
                usersListFacade.updateDepartment(action.department)
            }

            is FilterByInputSearchAction -> {
                usersListFacade.updateInputSearch(action.inputSearch)
            }

            is RefreshAction -> {
                refreshUsers()
            }

            is SortByAction -> {
                usersListFacade.updateSortItem(action.sortType.sorterItem)
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
                    usersListFacade.setList(users)

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
                    usersListFacade.addNewList(newUsers)

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