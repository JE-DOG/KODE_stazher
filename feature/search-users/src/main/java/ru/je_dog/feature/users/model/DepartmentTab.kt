package ru.je_dog.feature.users.model

enum class DepartmentTab(
    val text: String,
    val tag: String?
){

    All(
        "Все",
        null
    ),
    Android(
        "Android",
        "android"
    ),
    IOS(
        "iOS",
        "ios"
    ),
    Design(
        "Дизайн",
        "design"
    ),
    Management(
        "Менеджмент",
        "management"
    ),
    QA(
        "QA",
        "qa"
    ),
    BackOffice(
        "Бэк",
        "back_office"
    ),
    Frontend(
        "Frontend",
        "frontend"
    ),
    HR(
        "HR",
        "hr"
    ),
    PR(
        "PR",
        "pr"
    ),
    Backend(
        "Backend",
        "backend"
    ),
    Support(
        "Техподдержка",
        "support"
    ),
    Analytics(
        "Аналитика",
        "analytics"
    )


}
