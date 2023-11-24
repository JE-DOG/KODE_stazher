package ru.je_dog.users.use_cases

import ru.je_dog.users.UsersRepository

class GetUsersUseCases(
    private val usersRepository: UsersRepository
) {

    suspend fun execute() = usersRepository.getUsers()

}