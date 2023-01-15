package com.vivosense.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val street: String,
    val streetNumber: Int,
    val unit: String?,
    val zip: String,
    val city: String,
    val county: String
)