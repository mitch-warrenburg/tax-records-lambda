package com.vivosense

// AWS constants
const val AWS_REGION = "us-west-1"
const val S3_BUCKET_NAME = "county-tax-records"

// Mongo constants
const val COLLECTION_NAME = "tax-payments"
const val DATABASE_NAME = "property-tax-records"

// Failure retry constants
const val FAILURE_RETRY_COUNT = 3
const val FAILURE_RETRY_BACKOFF_SECONDS = 30

// SQS constants
const val SQS_SUCCESS_QUEUE_NAME = "property-tax-records-success-queue"
const val SQS_FAILURE_QUEUE_NAME = "property-tax-records-failure-queue"
const val SQS_DEAD_LETTER_QUEUE_NAME = "property-tax-records-dead-letter-queue"

// SQS messaging attribute constants
const val STRING_ATTRIBUTE_TYPE = "String"
const val NUMBER_ATTRIBUTE_TYPE = " Number"
const val RETRY_ATTRIBUTE_KEY = "retryCount"
const val PROCESSED_TIME_ATTRIBUTE_KEY = "processedTime"

// environment variable names
const val MONGO_URL_ENV_VAR = "MONGO_URL"
const val MONGO_USER_ENV_VAR = "MONGO_USER"
const val MONGO_PASSWORD_ENV_VAR = "MONGO_PASSWORD"
const val MONGO_MONGO_CREDENTIAL_SOURCE_ENV_VAR = "MONGO_MONGO_CREDENTIAL_SOURCE"

