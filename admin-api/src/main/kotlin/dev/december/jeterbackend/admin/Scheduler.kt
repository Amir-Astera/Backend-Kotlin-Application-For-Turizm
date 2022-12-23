package dev.december.jeterbackend.admin

import dev.december.jeterbackend.admin.features.promocodes.presentation.rest.PromocodeController
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler (
    private val promocodeController: PromocodeController,
        ){

    private val logger = LoggerFactory.getLogger(JeterAdminApplication::class.java)

    @Scheduled(cron = "@midnight")
    fun promocodeExpirationJob(){
        promocodeController.expirationJob().subscribe()
    }
}