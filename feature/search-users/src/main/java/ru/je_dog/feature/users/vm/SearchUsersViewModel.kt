package ru.je_dog.feature.users.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.je_dog.core.common.ext.isSubstringFor
import ru.je_dog.core.data.common.exception.FailedRequestException
import ru.je_dog.core.feature.common.ext.sortByMonth
import ru.je_dog.core.feature.model.UserPresentation
import ru.je_dog.feature.users.model.SearchUsersDepartmentTab
import ru.je_dog.feature.users.model.SearchUsersSortType
import ru.je_dog.feature.users.vm.action.ClickOnUserItemAction
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
    private val getUsersWithErrorUseCase: GetUsersWithErrorUseCase,
    private val navController: NavHostController
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

    private fun setDepartmentFilter(
        department: SearchUsersDepartmentTab
    ) {

        val filteredList = filterUsers(
            department = department
        )

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

        val filteredList = filterUsers(
            inputSearch = inputSearch
        )

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

    private fun sortUsers(
        sortType: SearchUsersSortType
    ) {

        val users = state.value.usersList

        when(sortType){

            SearchUsersSortType.Alphabet -> {

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

            SearchUsersSortType.Birthday -> {

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

    private fun sortUsers(
        users: List<UserPresentation>
    ) {

        val sortType = state.value.sortType

        when(sortType){

            SearchUsersSortType.Alphabet -> {

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

            SearchUsersSortType.Birthday -> {

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

    private fun filterUsers(
        users: List<UserPresentation> = state.value.usersList,
        inputSearch: String? = state.value.searchInputFilter,
        department: SearchUsersDepartmentTab = state.value.departmentFilter
    ): List<UserPresentation> {

        val stateValue = state.value
        var filteredList = mutableListOf<UserPresentation>()

        if (department == SearchUsersDepartmentTab.All && inputSearch == null)
            return users

        for (user in users){

            if (department != SearchUsersDepartmentTab.All){

                if (department.tag == user.department) {


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

}