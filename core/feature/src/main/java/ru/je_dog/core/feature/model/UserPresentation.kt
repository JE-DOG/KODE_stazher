package ru.je_dog.core.feature.model

import ru.je_dog.core.domain.model.UserDomain

/*
{
    "id": "e0fceffa-cef3-45f7-97c6-6be2e3705927",
    "avatarUrl": "https://cdn.fakercloud.com/avatars/marrimo_128.jpg",
    "firstName": "Dee",
    "lastName": "Reichert",
    "userTag": "LK",
    "department": "back_office",
    "position": "Technician",
    "birthday": "2004-08-02",
    "phone": "802-623-1785"
}
* */

data class UserPresentation(
    val id: String,
    val firstname: String,
    val lastname: String,
    val userTag: String,
    val department: String,
    val position: String,
    val birthday: String,
    val phone: String
){

    companion object {

        fun fromDomain(
            userDomain: UserDomain
        ): UserPresentation = userDomain.run {
            UserPresentation(
                id,
                firstname,
                lastname,
                userTag,
                department,
                position,
                birthday,
                phone
            )
        }

    }
}