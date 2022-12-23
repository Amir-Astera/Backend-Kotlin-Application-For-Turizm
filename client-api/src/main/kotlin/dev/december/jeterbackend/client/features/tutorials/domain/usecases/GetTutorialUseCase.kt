package dev.december.jeterbackend.client.features.tutorials.domain.usecases

import dev.december.jeterbackend.shared.features.tutorials.domain.models.Tutorial
import dev.december.jeterbackend.client.features.tutorials.domain.services.TutorialService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class GetTutorialUseCase(
    private val tutorialService: TutorialService
) : UseCase<GetTutorialParams, Tutorial> {
    override suspend fun invoke(params: GetTutorialParams): Data<Tutorial> {
        return tutorialService.get(params.id)
    }
}

data class GetTutorialParams(
    val id: String
)