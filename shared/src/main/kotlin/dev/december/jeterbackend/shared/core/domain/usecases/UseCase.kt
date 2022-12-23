package dev.december.jeterbackend.shared.core.domain.usecases

import dev.december.jeterbackend.shared.core.results.Data


interface UseCase<in P : Any, T : Any> {
    suspend operator fun invoke(params: P): Data<T>
}