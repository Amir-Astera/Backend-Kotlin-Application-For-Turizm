package dev.december.jeterbackend.admin.features.terrain.domain.usecases

//
//@Component
//class UpdateSpecialityUseCase(
//    private val specialityService: SpecialityService
//) : UseCase<UpdateSpecialityParams, Unit> {
//    override suspend fun invoke(params: UpdateSpecialityParams): Data<Unit> {
//        return specialityService.update(
//            params.id, params.title, params.priority, params.description, params.profession, params.fileId
//        )
//    }
//}
//
//data class UpdateSpecialityParams(
//    val id: String,
//    val title: String?,
//    val priority: Int?,
//    val description: String?,
//    val profession: String?,
//    val fileId: File?
//)