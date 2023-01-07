package dev.december.jeterbackend.shared.features.promocodes.data.services

import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.admin.data.repositories.AdminRepository
import dev.december.jeterbackend.shared.features.promocodes.data.entities.PromocodeClientActivatedEntity
import dev.december.jeterbackend.shared.features.promocodes.data.entities.PromocodeEntity
import dev.december.jeterbackend.shared.features.promocodes.data.entities.extensions.promocode
import dev.december.jeterbackend.shared.features.promocodes.data.repositories.PromocodeClientActivatedRepository
import dev.december.jeterbackend.shared.features.promocodes.data.repositories.PromocodeRepository
import dev.december.jeterbackend.shared.features.promocodes.data.repositories.specifications.PromocodeSpecification
import dev.december.jeterbackend.shared.features.promocodes.domain.errors.*
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import dev.december.jeterbackend.shared.features.promocodes.domain.models.*
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.domain.errors.SupplierNotFoundFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PromocodeServiceImpl(
    private val promocodeRepository: PromocodeRepository,
    private val supplierRepository: SupplierRepository,
    private val adminRepository: AdminRepository,
    private val pcaRepository: PromocodeClientActivatedRepository,
    private val dispatcher: CoroutineDispatcher,
) : PromocodeService {

    override suspend fun create(
        code: String,
        description: String?,
        discountType: PromocodeDiscountType,
        discountAmount: Int?,
        discountPercentage: Double?,
        activationLimit: Int,
        validityFrom: LocalDateTime,
        validityTo: LocalDateTime,
        adminId: String?,
        userId: String?,
    ): Data<String> {
        return try {
            withContext(dispatcher) {
//                if (promocodeRepository.existsByCode(code))
//                    return@withContext Data.Error(PromocodeTitleAlreadyExistsFailure(code))
//
//                val adminEntity = if (adminId != null) {
//                    adminRepository.findByIdOrNull(adminId)
//                        ?: return@withContext Data.Error(AdminNotFoundFailure())
//                } else null
//
//                val supplierEntity = if (userId != null) {
//                    (userRepository.findByIdOrNull(userId)?: return@withContext Data.Error(UserNotFoundFailure()))
//                        .supplier ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                } else null
//
//                if ((discountType == PromocodeDiscountType.FIXED_AMOUNT && (discountAmount == null))
//                    || (discountType == PromocodeDiscountType.PERCENTAGE && (discountPercentage == null))){
//                    return@withContext Data.Error(PromocodeTypeWrongFailure())
//                }
//
//                if (activationLimit <= 0) return@withContext Data.Error(PromocodeActivationLimitFailure())
//
//                val promocodeEntity = promocodeRepository.save(
//                    PromocodeEntity(
//                        code = code,
//                        description = description,
//                        discountType = discountType,
//                        discountAmount = discountAmount,
//                        discountPercentage = discountPercentage,
//                        activationLimit = activationLimit,
//                        validityFrom = validityFrom,
//                        validityTo = validityTo,
//                        status = PromocodeStatus.INACTIVE,
//                        admin = adminEntity,
//                        supplier = supplierEntity,
//                    )
//                )
                Data.Success("")//promocodeEntity.id
            }
        } catch (e: Exception) {
            Data.Error(PromocodeCreateFailure())
        }
    }

    override suspend fun update(
        promoId: String,
        adminId: String?,
        userId: String?,
        code: String?,
        description: String?,
        discountType: PromocodeDiscountType?,
        discountAmount: Int?,
        discountPercentage: Double?,
        activationLimit: Int?,
        validityFrom: LocalDateTime?,
        validityTo: LocalDateTime?,
        updatedAt: LocalDateTime,
    ): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val oldEntity =
//                    promocodeRepository.findByIdOrNull(promoId) ?: return@withContext Data.Error(PromocodeNotFoundFailure())
//
//                if (oldEntity.activatedAmount > 0) return@withContext Data.Error(PromocodeUpdateAlreadyActivatedFailure())
//
//                if (userId != null) {
//                    val supplierEntity = (userRepository.findByIdOrNull(userId)
//                        ?: return@withContext Data.Error(UserNotFoundFailure()))
//                        .supplier ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                    if (oldEntity.supplier != supplierEntity) return@withContext Data.Error(PromocodeNotSuppliersFailure(oldEntity.code))
//                }
//
//                if (code != null) {
//                    val tmp = promocodeRepository.findByCode(code).orElse(null)
//                    if (tmp != null && tmp != oldEntity)
//                        return@withContext Data.Error(PromocodeTitleAlreadyExistsFailure(code))
//                }
//
//                if (discountType != null){
//                    if ((discountType == PromocodeDiscountType.FIXED_AMOUNT && (discountAmount == null))
//                        || (discountType == PromocodeDiscountType.PERCENTAGE && (discountPercentage == null))){
//                        return@withContext Data.Error(PromocodeTypeWrongFailure())
//                    }
//                } else if (discountAmount != null && oldEntity.discountType != PromocodeDiscountType.FIXED_AMOUNT) {
//                    return@withContext Data.Error(PromocodeTypeWrongFailure())
//                } else if (discountPercentage != null && oldEntity.discountType != PromocodeDiscountType.PERCENTAGE) {
//                    return@withContext Data.Error(PromocodeTypeWrongFailure())
//                }
//
//                val status = if ((validityTo != null && validityTo > LocalDateTime.now()
//                            && oldEntity.status == PromocodeStatus.EXPIRED)) {
//                    PromocodeStatus.INACTIVE
//                } else null
//
//                if (validityTo != null) {
//                    if (validityTo.isBefore(LocalDateTime.now())) {
//                        return@withContext Data.Error(PromocodeValidToFailure())
//                    }
//                }
//                if (validityFrom != null) {
//                    if (validityFrom.isBefore(LocalDateTime.now())) {
//                        return@withContext Data.Error(PromocodeValidToFailure())
//                    }
//                }
//
//                promocodeRepository.save(
//                    oldEntity.copy(
//                        code = code ?: oldEntity.code,
//                        description = description ?: oldEntity.description,
//                        discountType = discountType ?: oldEntity.discountType,
//                        discountAmount = discountAmount,
//                        discountPercentage = discountPercentage,
//                        activationLimit = activationLimit ?: oldEntity.activationLimit,
//                        validityFrom = validityFrom ?: oldEntity.validityFrom,
//                        validityTo = validityTo ?: oldEntity.validityTo,
//                        status = status ?: oldEntity.status,
//                        updatedAt = updatedAt,
//                    )
//                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(PromocodeUpdateFailure())
        }
    }

    override suspend fun disableList(
        ids: List<String>,
    ): Data<Unit> {
        return try {
            withContext(dispatcher) {
                ids.map {
                    val promocodeEntity = promocodeRepository.findByIdOrNull(it) ?: return@withContext Data.Error(
                        PromocodeNotFoundFailure()
                    )
                    promocodeRepository.save(
                        promocodeEntity.copy(
                            status = PromocodeStatus.INACTIVE,
                        )
                    )
                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(PromocodeListDeleteFailure())
        }
    }

    override suspend fun get(
        id: String,
        userId: String?,
    ): Data<Promocode> {
        return try {
            withContext(dispatcher) {
                val promocodeEntity =
                    promocodeRepository.findByIdOrNull(id) ?: return@withContext Data.Error(PromocodeNotFoundFailure())
//
//                if (userId != null) {
//                    val supplierEntity = (userRepository.findByIdOrNull(userId)
//                        ?: return@withContext Data.Error(UserNotFoundFailure()))
//                        .supplier ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                    if (promocodeEntity.supplier != supplierEntity)
//                        return@withContext Data.Error(PromocodeNotSuppliersFailure(promocodeEntity.code))
//                }

                val promocode = promocodeEntity.promocode()
                Data.Success(promocode)
            }
        } catch (e: Exception) {
            Data.Error(PromocodeGetFailure())
        }
    }

    override suspend fun getList(
        userId: String?,
        supplierId: String?,
        statuses: Set<PromocodeStatus>?,
        types: Set<PromocodeDiscountType>?,
        searchField: String?,
        sortField: PromocodeSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<Promocode>> {
        return try {
            withContext(dispatcher) {
                val sortParams = sortField.getSortFields(sortDirection)

                val pageable = PageRequest.of(page, size, sortParams)

                val specificationByUserRole = if (userId != null) {
                    val idSupplier =  supplierRepository.findByIdOrNull(userId)?.id ?: return@withContext Data.Error(SupplierNotFoundFailure())
                    PromocodeSpecification.supplierJoinFilter(idSupplier)
                } else if (supplierId != null) {
                    supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(SupplierNotFoundFailure())
                    PromocodeSpecification.supplierJoinFilter(supplierId)
                } else null
                val specifications =
                    Specification.where(specificationByUserRole)
                        .and(PromocodeSpecification.hasDiscountType(types))
                        .and(PromocodeSpecification.containsCode(searchField))
                        .and(PromocodeSpecification.isGreaterThanCreatedAt(createdFrom))
                        .and(PromocodeSpecification.isLessThanCreatedAt(createdTo))
                        .and(PromocodeSpecification.hasStatus(statuses))

                val promocodeEntities = promocodeRepository.findAll(specifications, pageable)
                val promocodes = promocodeEntities.map {
                    it.promocode()
                }
                Data.Success(promocodes)
            }
        } catch (e: Exception) {
            Data.Error(PromocodeGetFailure())
        }
    }

    override suspend fun updateStatus(id: String, userId: String?, status: PromocodeStatus): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val promocodeEntity = promocodeRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(PromocodeNotFoundFailure())

//                if (userId != null) {
//                    val supplierEntity = (userRepository.findByIdOrNull(userId)
//                        ?: return@withContext Data.Error(UserNotFoundFailure()))
//                        .supplier ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                    if (promocodeEntity.supplier != supplierEntity)
//                        return@withContext Data.Error(PromocodeNotSuppliersFailure(promocodeEntity.code))
//                }

                promocodeRepository.save(
                    promocodeEntity.copy(
                        status = status,
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(PromocodeStatusUpdateFailure())
        }
    }

    override suspend fun generateTitle(): Data<String> {
        return try {
            var title: String
            while (true) {
                title = getRandomString(10)
                if (!promocodeRepository.existsByCode(title))
                    break
            }
            Data.Success(title)
        } catch (e: Exception) {
            Data.Error(PromocodeTitleGenerateFailure())
        }
    }

    override suspend fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    override suspend fun delete(id: String, userId: String?): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val promocodeEntity = promocodeRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(PromocodeNotFoundFailure())

//                if (userId != null) {
//                    val supplierEntity = (userRepository.findByIdOrNull(userId)
//                        ?: return@withContext Data.Error(UserNotFoundFailure()))
//                        .supplier ?: return@withContext Data.Error(SupplierNotFoundFailure())
//                    if (promocodeEntity.supplier != supplierEntity)
//                        return@withContext Data.Error(PromocodeNotSuppliersFailure(promocodeEntity.code))
//                }

                promocodeRepository.delete(promocodeEntity)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(PromocodeStatusUpdateFailure())
        }
    }

    override suspend fun expirationJob(): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val specification = Specification.where(PromocodeSpecification.isExpired(LocalDateTime.now()))
                promocodeRepository.findAll(specification).forEach { promocodeEntity ->
                    promocodeRepository.save(promocodeEntity.copy(
                    status = PromocodeStatus.EXPIRED,
                    updatedAt = LocalDateTime.now())
                    )
                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(PromocodeExpirationJobFailure())
        }
    }

    override suspend fun validatePromocode(code: String, supplierId: String, userId: String): Data<Promocode> {
        return try {
            withContext(dispatcher) {
                val promocodeEntity = promocodeRepository.findByCode(code).orElse(null)
                    ?: return@withContext Data.Error(PromocodeNotFoundFailure())
//
//                val clientEntity = (userRepository.findByIdOrNull(userId) ?:
//                return@withContext Data.Error(UserNotFoundFailure())).client
//                    ?: return@withContext Data.Error(UserNotFoundFailure())
//
//                val supplier = userRepository.findBySupplierId(supplierId)?.supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                if (promocodeEntity.supplier != null) {
//                    if (promocodeEntity.supplier != supplier) {
//                        return@withContext Data.Error(PromocodeNotSuppliersFailure(promocodeEntity.code))
//                    }
//                }
//
//                if (promocodeEntity.status != PromocodeStatus.ACTIVE) { return@withContext Data.Error(PromocodeStatusFailure())}
//
//                if (promocodeEntity.activatedAmount >= promocodeEntity.activationLimit) {
//                    promocodeRepository.save(promocodeEntity.copy(status = PromocodeStatus.EXPIRED, updatedAt = LocalDateTime.now()))
//                    return@withContext Data.Error(PromocodeActivationExceedFailure())
//                }
////                val specification = Specification.where(PromocodeClientActivatedSpecification.clientJoinFilter(clientEntity.id))
//                val clientActivations =
//                    pcaRepository.findAllByClient(clientEntity)
////                    pcaRepository.findAll(specification).toList()
//                if (clientActivations.isNotEmpty()) {
//                    clientActivations.forEach { it ->
//                        if (it.promocode.id == promocodeEntity.id) {
//                            return@withContext Data.Error(PromocodeClientAlreadyActivatedFailure())
//                        }
//                    }
//                }
//                promocodeRepository.save(promocodeEntity.copy(totalAttempts = promocodeEntity.totalAttempts.plus(1)))
                Data.Success(promocodeEntity.promocode())
            }
        } catch (e: Exception) {
            Data.Error(PromocodeValidationFailure())
        }
    }

//    override suspend fun getDiscount(promocode: Promocode): Data<Any> {
//        return try {
//            withContext(dispatcher) {
//                if ((promocode.discountType == PromocodeDiscountType.FIXED_AMOUNT) && (promocode.discountAmount != null)){
//                    return@withContext Data.Success(promocode.discountAmount)
//                } else if ((promocode.discountType == PromocodeDiscountType.PERCENTAGE) && (promocode.discountPercentage != null)) {
//                    return@withContext Data.Success(promocode.discountPercentage)
//                } else return@withContext Data.Error(PromocodeTypeWrongFailure())
//            }
//        } catch (e: Exception) {
//            Data.Error(PromocodeGetDiscountFailure())
//        }
//    }

    override suspend fun getFinalPrice(promocode: Promocode, price: Double): Data<PromocodeDiscountAmount> {
        return try {
            withContext(dispatcher) {
                val finalPrice: Double
                val promocodeDiscountAmount: PromocodeDiscountAmount
                if ((promocode.discountType == PromocodeDiscountType.FIXED_AMOUNT) && (promocode.discountAmount != null)){
                    finalPrice = price - promocode.discountAmount
                    promocodeDiscountAmount = PromocodeDiscountAmount(
                        discountAmount = promocode.discountAmount.toDouble(),
                        finalPrice = finalPrice,
                        promocode = promocode
                    )
                    return@withContext Data.Success(promocodeDiscountAmount)
                } else if ((promocode.discountType == PromocodeDiscountType.PERCENTAGE) && (promocode.discountPercentage != null)) {
                    finalPrice = (price*(100-promocode.discountPercentage))/100
                    promocodeDiscountAmount = PromocodeDiscountAmount(
                        discountAmount = (price*promocode.discountPercentage)/100,
                        finalPrice = finalPrice,
                        promocode = promocode
                    )
                    return@withContext Data.Success(promocodeDiscountAmount)
                } else return@withContext Data.Error(PromocodeTypeWrongFailure())
            }
        } catch (e: Exception) {
            Data.Error(PromocodeGetDiscountFailure())
        }
    }

    override suspend fun applyPromocode(promocodeId: String, userId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
//                val clientEntity = (userRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(UserNotFoundFailure()))
//                    .client ?: return@withContext Data.Error(UserNotFoundFailure())

                val promocodeEntity = promocodeRepository.findByIdOrNull(promocodeId) ?: return@withContext Data.Error(PromocodeNotFoundFailure())

                promocodeRepository.save(promocodeEntity.copy(
                    activatedAmount = promocodeEntity.activatedAmount.plus(1)
                ))
//                pcaRepository.save(PromocodeClientActivatedEntity(
//                    promocode = promocodeEntity,
////                    client = clientEntity
//                ))

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(PromocodeGetDiscountFailure())
        }
    }
}