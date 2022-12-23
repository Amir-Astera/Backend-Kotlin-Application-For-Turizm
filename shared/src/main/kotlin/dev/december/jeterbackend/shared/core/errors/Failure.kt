package dev.december.jeterbackend.shared.core.errors

interface Failure {
    val code: Int
    val message: String
}