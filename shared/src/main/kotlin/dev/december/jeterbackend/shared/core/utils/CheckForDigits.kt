package dev.december.jeterbackend.shared.core.utils

//check if login contains only number (if yes, it is phone number)
fun check(login: String): Boolean {
    val size: Int = login.length
    for (i in 0 until size) {
        if (login[i] !in '0'..'9'
            || login[i] != '+' || login[i] != '(' || login[i] != ')'){
            return false
        }
    }
    return true
}
