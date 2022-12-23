package dev.december.jeterbackend.stream.core.usecases

import dev.december.jeterbackend.shared.core.results.Data

interface UseCase<T : Any, in P : Any> {
  operator fun invoke(param: P): Data<T>
}