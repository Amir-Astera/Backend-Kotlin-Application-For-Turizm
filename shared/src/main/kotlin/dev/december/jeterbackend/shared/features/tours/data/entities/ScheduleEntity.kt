package dev.december.jeterbackend.shared.features.tours.data.entities

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import java.util.*
import javax.persistence.*

@Entity(name = "schedule")
data class  ScheduleEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),

    @OneToOne(mappedBy = "schedule")
    private val supplier: SupplierEntity? = null,

    @Column(name = "monday")
    val monday: String? = null,
    @Column(name = "monday_break")
    val mondayBreak: String? = null,

    @Column(name = "tuesday")
    val tuesday: String? = null,
    @Column(name = "tuesday_break")
    val tuesdayBreak: String? = null,

    @Column(name = "wednesday")
    val wednesday: String? = null,
    @Column(name = "wednesday_break")
    val wednesdayBreak: String? = null,

    @Column(name = "thursday")
    val thursday: String? = null,
    @Column(name = "thursday_break")
    val thursdayBreak: String? = null,

    @Column(name = "friday")
    val friday: String? = null,
    @Column(name = "friday_break")
    val fridayBreak: String? = null,

    @Column(name = "saturday")
    val saturday: String? = null,
    @Column(name = "saturday_break")
    val saturdayBreak: String? = null,

    @Column(name = "sunday")
    val sunday: String? = null,
    @Column(name = "sunday_break")
    val sundayBreak: String? = null,
) {

    companion object {

        const val defaultWorkPeriod = "09:00-22:00"
        const val defaultBreakPeriod = "13:00-14:00"
        const val breakBeforeNextAppointment = 20L
        const val appointmentDuration = 40L
    }

}