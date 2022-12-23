package dev.december.jeterbackend.client.features.tutorials.domain.usecases

import dev.december.jeterbackend.shared.features.tutorials.domain.models.Tutorial
import dev.december.jeterbackend.client.features.tutorials.domain.services.TutorialService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class GetTutorialListUseCase(
    private val tutorialService: TutorialService
) : UseCase<GetTutorialListParams, List<Tutorial>> {
    override suspend fun invoke(params: GetTutorialListParams): Data<List<Tutorial>> {
        return tutorialService.getAll(params.authority)
    }
}

data class GetTutorialListParams(
    val authority: PlatformRole
)
