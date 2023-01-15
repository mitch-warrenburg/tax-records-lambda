package com.vivosense.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
data class TaxPaymentRecord(
    val id: String,
    val confirmationNumber: String,
    val propertyAddress: Address,
    val propertyOwner: PropertyOwner,
    val taxYear: Int,
    val installmentPeriod: TaxInstallmentPeriod,
    val amount: Double,
    val amountOwed: Double,
    val remainingBalance: Double,
    val taxRatePercent: Double,
    val assessedPropertyValue: Double,
    val isDelinquentBackPayment: Boolean,
    @Serializable(with = LocalDateTimeIso8601Serializer::class) val paymentDate: LocalDateTime,
    @Serializable(with = LocalDateTimeIso8601Serializer::class) var createdDate: LocalDateTime? = null,
)

