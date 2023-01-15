package com.vivosense.service

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential.createCredential
import com.mongodb.WriteConcern.MAJORITY
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.result.InsertManyResult
import com.vivosense.*
import org.bson.Document
import java.lang.System.getenv

class MongoService {

    fun insertDocuments(documents: MutableList<Document>): InsertManyResult {
        val mongoClient = createMongoClient()
        val collection = mongoClient
            .getDatabase(DATABASE_NAME)
            .getCollection(COLLECTION_NAME)

        return collection.insertMany(documents)
    }

    private fun createMongoClient(): MongoClient {
        val url = getenv(MONGO_URL_ENV_VAR)
        val username = getenv(MONGO_USER_ENV_VAR)
        val password = getenv(MONGO_PASSWORD_ENV_VAR)
        val credentialSource = getenv(MONGO_MONGO_CREDENTIAL_SOURCE_ENV_VAR)
        val credential = createCredential(username, credentialSource, password.toCharArray())
        val settings = MongoClientSettings.builder()
            .retryWrites(true)
            .writeConcern(MAJORITY)
            .credential(credential)
            .applyConnectionString(ConnectionString(url))
            .build()

        return MongoClients.create(settings)
    }
}