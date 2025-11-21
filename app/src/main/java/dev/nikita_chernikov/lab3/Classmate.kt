package dev.nikita_chernikov.lab3

import java.util.Date

data class Classmate(var id: Int = 0, var firstName: String, var lastName: String, var patronymic: String?, val createdAt: Date = Date())
{
    val fullName: String
        get() = listOfNotNull(lastName, firstName, patronymic).joinToString(" ")
}
