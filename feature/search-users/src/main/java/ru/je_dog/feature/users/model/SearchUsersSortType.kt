package ru.je_dog.feature.users.model

enum class SearchUsersSortType(
    val text: String
) {

    Birthday(
        "По дню рождения"
    ),
    Alphabet(
        "По алфавиту"
    )

}