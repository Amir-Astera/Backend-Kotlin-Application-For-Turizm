package dev.december.jeterbackend.shared.core.results

import dev.december.jeterbackend.shared.core.errors.Failure


sealed class Data<T : Any> {
    class Success<T : Any>(val data: T) : Data<T>()
    class Error<T : Any>(val failure: Failure) : Data<T>()


    suspend fun <D : Any> fold(transform: suspend (T) -> Data<D>): Data<D> {
        return when (this) {
            is Success -> transform(this.data)
            is Error -> Error(this.failure)
        }
    }
}