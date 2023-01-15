package com.vivosense.service

import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.vivosense.AWS_REGION
import com.vivosense.S3_BUCKET_NAME

class S3Service {

    fun getDocumentContents(objectKey: String): String {
        val s3Client = createS3Client()
        val recordsFile = s3Client.getObject(S3_BUCKET_NAME, objectKey)

        return recordsFile.objectContent.bufferedReader().use { it.readText() }
    }

    private fun createS3Client() = AmazonS3ClientBuilder.standard()
        .withRegion(AWS_REGION)
        .build()
}
