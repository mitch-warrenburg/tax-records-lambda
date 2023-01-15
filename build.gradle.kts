import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    java
    application
    id("io.ktor.plugin") version "2.2.2"
    id("org.jetbrains.kotlin.jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.vivosense"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.litote.kmongo:kmongo:4.8.0")
    implementation("org.litote.kmongo:kmongo-coroutine:4.8.0")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.385")
    implementation("com.amazonaws:aws-java-sdk-sqs:1.12.385")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
}

application {
    mainClass.set("com.vivosense.lambda.TaxRecordsProcessor")
}

configurations["runtimeElements"].attributes {
    attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 11)
}