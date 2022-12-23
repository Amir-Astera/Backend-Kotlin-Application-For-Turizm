package dev.december.jeterbackend.client.features.orders.data.services

import dev.december.jeterbackend.client.core.config.CloudPaymentsConfig
import dev.december.jeterbackend.client.features.clients.domain.errors.ClientNotFoundFailure
import dev.december.jeterbackend.client.features.orders.domain.errors.OrderCreateException
import dev.december.jeterbackend.client.features.orders.domain.errors.OrderCreateFailure
import dev.december.jeterbackend.client.features.orders.domain.errors.OrderPayWithSecureException
import dev.december.jeterbackend.client.features.orders.domain.services.OrderService
import dev.december.jeterbackend.client.features.orders.presentation.dta.SecureOrderData
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.orders.data.entities.OrderEntity
import dev.december.jeterbackend.shared.features.orders.data.repositories.OrderRepository
import dev.december.jeterbackend.shared.features.orders.domain.models.*
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.domain.errors.SupplierNotFoundFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.*
import java.util.*


@Service
class OrderServiceImpl(
    webClientBuilder: WebClient.Builder,
    private val dispatcher: CoroutineDispatcher,
    private val supplierRepository: SupplierRepository,
    private val clientRepository: ClientRepository,
    private val cloudPaymentsConfig: CloudPaymentsConfig,
    private val orderRepository: OrderRepository
) : OrderService {
    private val webClient = webClientBuilder.baseUrl(cloudPaymentsConfig.baseUrl).build()

    override suspend fun pay(
        clientId: String,
        supplierId: String,
        cardCryptogram: String,
        communicationType: CommunicationType,
        ipAddress: String
    ): Data<Unit> {//Order
        return try {
            withContext(dispatcher) {
                val supplier = supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(SupplierNotFoundFailure())
                val client = clientRepository.findByIdOrNull(clientId) ?: return@withContext Data.Error(ClientNotFoundFailure())
                val amount = when (communicationType) {
                    CommunicationType.AUDIO -> supplier.audioPerHour ?: return@withContext Data.Error(SupplierNotFoundFailure())
                    CommunicationType.VIDEO -> supplier.videoPerHour ?: return@withContext Data.Error(SupplierNotFoundFailure())
                }

                val order = OrderData(
                    amount = amount,
                    invoiceId = UUID.randomUUID().toString(),
                    currency = "KZT",
                    ipAddress = ipAddress,
                    description = "Payment for a ${communicationType.name.lowercase()} session",
                    accountId = client.id,
                    cardCryptogramPacket = cardCryptogram,
//                    email = client.user?.email
                )
               val response = webClient.post()
                    .uri("/payments/cards/charge")
                   .contentType(MediaType.APPLICATION_JSON)
                   .header(HttpHeaders.AUTHORIZATION, cloudPaymentsConfig.basicAuthHeader)
                   .bodyValue(order)
                   .retrieve()
                   .awaitBody<PayCloudPaymentsResponse>()

                val orderResponse = if(response.success && response.model!!.reasonCode == 0) {
                    orderRepository.save(OrderEntity(
                        invoiceId = response.model?.invoiceId!!,
                        amount = response.model?.amount!!,
                        description = response.model?.description!!,
                        transactionId = response.model?.transactionId!!,
                        token = response.model?.token!!,
                        clientId = client,
                    ))
                    Order(model = response.model!!, status = ResponseStatus.COMPLETED, success = true, message = response.model?.cardHolderMessage!!)
                } else if(!response.success && (response.model!!.reasonCode != 0 && response.model!!.reasonCode != null)){
                    Order(model = response.model!!, status = ResponseStatus.ERROR, success = false, message = response.model?.cardHolderMessage!!)
                } else if(!response.success && response.model?.paReq != null){
                    Order(model = response.model!!, status = ResponseStatus.SECURE, success = false)
                } else {
                    return@withContext Data.Error(OrderCreateFailure(message = response.message!!))
                }

                Data.Success(Unit)//orderResponse
            }
        } catch (e: Exception) {
            Data.Error(OrderCreateException())
        }
    }

    override suspend fun payWithSecure(
        md: Long,
        paRes: String,
        clientId: String
    ): Data<Unit> {//Order
        return try {
            withContext(dispatcher) {
                val client = clientRepository.findByIdOrNull(clientId) ?: return@withContext Data.Error(ClientNotFoundFailure())
                val order = SecureOrderData(md, paRes)
                val response = webClient.post()
                    .uri("/payments/cards/post3ds")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, cloudPaymentsConfig.basicAuthHeader)
                    .bodyValue(order)
                    .retrieve()
                    .awaitBody<PayCloudPaymentsResponse>()

                val orderResponse = if(response.success && response.model!!.reasonCode == 0) {
                    orderRepository.save(OrderEntity(
                        invoiceId = response.model?.invoiceId!!,
                        amount = response.model?.amount!!,
                        description = response.model?.description!!,
                        transactionId = response.model?.transactionId!!,
                        token = response.model?.token!!,
                        clientId = client,
                    ))
                    Order(model = response.model!!, status = ResponseStatus.COMPLETED, success = true, message = response.model?.cardHolderMessage!!)
                } else if(!response.success && (response.model!!.reasonCode != 0 && response.model!!.reasonCode != null)){
                    Order(model = response.model!!, status = ResponseStatus.ERROR, success = false, message = response.model?.cardHolderMessage!!)
                } else {
                    return@withContext Data.Error(OrderCreateFailure(message = response.message!!))
                }

                Data.Success(Unit)//orderResponse
            }
        } catch (e: Exception) {
            Data.Error(OrderPayWithSecureException())
        }
    }



}