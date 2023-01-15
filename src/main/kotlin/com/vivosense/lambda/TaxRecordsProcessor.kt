package com.vivosense.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.vivosense.model.TaxPaymentRecord
import com.vivosense.service.MongoService
import com.vivosense.service.S3Service
import com.vivosense.service.SqsService
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json.Default.decodeFromString
import kotlinx.serialization.json.Json.Default.encodeToString
import org.bson.Document
import org.litote.kmongo.bson
import java.time.LocalDateTime.now

@Suppress("unused", "unused_parameter")
class TaxRecordsProcessor {
    private val s3Service = S3Service()
    private val sqsService = SqsService()
    private val mongoService = MongoService()

    fun handleS3Request(event: S3Event, context: Context?) {
        val fileId = event.records.first().s3.`object`.key
        processRecords(fileId)
    }

    fun handleSqsRetryRequest(event: SQSEvent, context: Context?) {
        val fileId = event.records.first().body
        val retryCount = event.records.first().messageAttributes["retryCount"]?.stringValue!!
        processRecords(fileId, retryCount.toInt())
    }

    private fun processRecords(fileId: String, retryCount: Int = 0) {
        try {
            val recordsJson = s3Service.getDocumentContents(fileId)
            val paymentRecords = decodeFromString(ListSerializer(TaxPaymentRecord.serializer()), recordsJson)
            val documents = paymentRecords
                .map { it.apply { createdDate = now().toKotlinLocalDateTime() } }
                .map { encodeToString(TaxPaymentRecord.serializer(), it) }
                .map { Document(it.bson) }
                .toMutableList()

            mongoService.insertDocuments(documents)
            sqsService.sendSuccessMessage(fileId)
        } catch (e: Exception) {
            sqsService.sendFailureMessage(fileId, retryCount)
        }
    }
}
