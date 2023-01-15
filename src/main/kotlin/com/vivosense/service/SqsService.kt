package com.vivosense.service

import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.CreateQueueRequest
import com.amazonaws.services.sqs.model.MessageAttributeValue
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.services.sqs.model.SendMessageResult
import com.vivosense.*
import java.time.LocalDateTime.now

class SqsService {

    fun sendSuccessMessage(message: String) {
        sendMessage(message, SQS_SUCCESS_QUEUE_NAME)
    }

    fun sendFailureMessage(message: String, retryCount: Int = 0) {
        if (retryCount <= FAILURE_RETRY_COUNT)
            sendMessageToFailureQueue(message, retryCount + 1)
        else
            sendMessageToDeadLetterQueue(message)
    }

    private fun sendMessageToFailureQueue(message: String, retryCount: Int = 0) {
        val retryCountAttribute = MessageAttributeValue()
            .withStringValue((retryCount).toString())
            .withDataType(NUMBER_ATTRIBUTE_TYPE)

        val attributes = mapOf(RETRY_ATTRIBUTE_KEY to retryCountAttribute)

        sendMessage(message, SQS_FAILURE_QUEUE_NAME, FAILURE_RETRY_BACKOFF_SECONDS, attributes)
    }

    private fun sendMessage(
        message: String,
        queueName: String,
        delaySeconds: Int = 0,
        additionalAttributes: Map<String, MessageAttributeValue> = emptyMap()
    ): SendMessageResult {
        val sqsClient = createSqsClient()
        val queueUrl = CreateQueueRequest(queueName).queueName
        val processedTimeAttribute = MessageAttributeValue()
            .withStringValue(now().toString())
            .withDataType(STRING_ATTRIBUTE_TYPE)

        val attributes = mutableMapOf(PROCESSED_TIME_ATTRIBUTE_KEY to processedTimeAttribute)

        attributes.putAll(additionalAttributes)

        val request = SendMessageRequest()
            .withQueueUrl(queueUrl)
            .withMessageBody(message)
            .withDelaySeconds(delaySeconds)
            .withMessageAttributes(attributes)

        return sqsClient.sendMessage(request)
    }

    private fun sendMessageToDeadLetterQueue(message: String) {
        sendMessage(message, SQS_DEAD_LETTER_QUEUE_NAME)
    }

    private fun createSqsClient() = AmazonSQSClientBuilder.standard()
        .withRegion(AWS_REGION)
        .build()
}