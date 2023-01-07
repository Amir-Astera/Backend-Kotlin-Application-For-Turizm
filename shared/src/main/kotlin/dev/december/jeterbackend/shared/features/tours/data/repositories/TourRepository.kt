package dev.december.jeterbackend.shared.features.tours.data.repositories


import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.shared.features.tours.data.entities.TourEntity
import dev.december.jeterbackend.shared.features.payments.domain.models.PaymentBySupplierInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TourRepository: PagingAndSortingRepository<TourEntity, String>, JpaSpecificationExecutor<TourEntity>, JpaRepository<TourEntity, String> {
    fun findByIdAndClientId(id: String, clientId: String): TourEntity?
    fun findByIdAndSupplierId(id: String, supplierId: String): TourEntity?

    fun findBySupplierIdAndAppointmentStatusInAndReservationDateBetween(
        supplierId: String,
        appointmentStatus: Set<AppointmentStatus>,
        reservationDateFrom: LocalDateTime,
        reservationDateTo: LocalDateTime
    ): List<TourEntity>

    @Query(
        "select sum(a.payment) from appointment a where a.supplier.id=:id  " +
                "and a.reservationDate >= :createdFrom and a.reservationDate <= :createdTo"
    )
    fun getTotalPaymentsInPeriod(id: String, createdFrom: LocalDateTime, createdTo: LocalDateTime): Int?

    @Query(
        "select sum(a.payment) from appointment a where a.supplier.id=:id  " +
                "and a.reservationDate <= :createdTo"
    )
    fun getTotalPaymentsToCreated(id: String, createdTo: LocalDateTime): Int?

    @Query(
        "select sum(a.payment) from appointment a where a.supplier.id=:id  " +
                "and a.reservationDate >= :createdFrom"
    )
    fun getTotalPaymentsFromCreated(id: String, createdFrom: LocalDateTime): Int?

    @Query("select sum(a.payment) from appointment a where a.supplier.id=:id")
    fun getTotalPayments(id: String): Int?

    @Query(
        "SELECT a.client.id as client, sum(a.payment) as payment, count(a.id) as sessionCount " +
                "FROM appointment a WHERE a.payment is not null " +
                "AND a.supplier.id = :supplierId GROUP BY a.client.id"
    )
    fun getPaymentsAndGroupBySupplierId(supplierId: String, pageable: Pageable): Page<PaymentBySupplierInterface>

    @Query(
        "SELECT a.client.id as client, sum(a.payment) as payment, count(a.id) as sessionCount " +
                "FROM appointment a WHERE a.payment is not null " +
                "AND a.supplier.id = :supplierId AND a.reservationDate >= :createdFrom " +
                "AND a.reservationDate <= :createdTo GROUP BY a.client.id"
    )
    fun getPaymentsAndGroupBySupplierIdInPeriod(
        supplierId: String,
        pageable: Pageable,
        createdFrom: LocalDateTime,
        createdTo: LocalDateTime
    ): Page<PaymentBySupplierInterface>

    @Query(
        "SELECT a.client.id as client, sum(a.payment) as payment, count(a.id) as sessionCount " +
                "FROM appointment a WHERE a.payment is not null " +
                "AND a.supplier.id = :supplierId AND a.reservationDate <= :createdTo GROUP BY a.client.id"
    )
    fun getPaymentsAndGroupBySupplierIdToCreated(
        supplierId: String,
        pageable: Pageable,
        createdTo: LocalDateTime
    ): Page<PaymentBySupplierInterface>

    @Query(
        "SELECT a.client.id as client, sum(a.payment) as payment, count(a.id) as sessionCount " +
                "FROM appointment a WHERE a.payment is not null " +
                "AND a.supplier.id = :supplierId AND a.reservationDate >= :createdFrom GROUP BY a.client.id"
    )
    fun getPaymentsAndGroupBySupplierIdFromCreated(
        supplierId: String,
        pageable: Pageable,
        createdFrom: LocalDateTime
    ): Page<PaymentBySupplierInterface>

    @Query(
        "select sum(a.payment) from appointment a where a.supplier.id=:id and a.client.id=:clientId " +
                "and a.reservationDate >= :createdFrom and a.reservationDate <= :createdTo"
    )
    fun getTotalPaymentsByClientInPeriod(
        id: String,
        clientId: String,
        createdFrom: LocalDateTime,
        createdTo: LocalDateTime
    ): Int?

    @Query(
        "select sum(a.payment) from appointment a where a.supplier.id=:id and a.client.id=:clientId " +
                "and a.reservationDate <= :createdTo"
    )
    fun getTotalPaymentsByClientToCreated(id: String, clientId: String, createdTo: LocalDateTime): Int?

    @Query(
        "select sum(a.payment) from appointment a where a.supplier.id=:id and a.client.id=:clientId " +
                "and a.reservationDate >= :createdFrom"
    )
    fun getTotalPaymentsByClientFromCreated(id: String, clientId: String, createdFrom: LocalDateTime): Int?

    @Query("select sum(a.payment) from appointment a where a.supplier.id=:id and a.client.id=:clientId")
    fun getTotalPaymentsByClient(id: String, clientId: String): Int?

    fun findAllBySupplierIdAndAppointmentStatusInAndReservationDateBetween(
        supplierId: String,
        appointmentStatus: Set<AppointmentStatus>,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<TourEntity>

    fun findAllByClientIdAndAppointmentStatusInAndReservationDateBetweenOrderByReservationDate(
        clientId: String,
        appointmentStatus: Set<TourStatus>,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<TourEntity>

    fun findAllBySupplierIdAndAppointmentStatusInAndReservationDateBetweenOrderByReservationDate(
        supplierId: String,
        appointmentStatus: Set<AppointmentStatus>,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<TourEntity>

    fun findAllBySupplierIdAndAppointmentStatusAndReservationDateBetween(
        id: String, status: AppointmentStatus, from: LocalDateTime, to: LocalDateTime
    ): List<TourEntity>

    fun findAllByClientIdAndSupplierId(
        clientId: String,
        supplierId: String,
    ): List<TourEntity>
}