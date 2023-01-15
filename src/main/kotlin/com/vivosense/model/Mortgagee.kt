package com.vivosense.model

import kotlinx.serialization.Serializable

@Serializable
data class Mortgagee(
    val institutionUuid: String,
    val name: String,
    val phoneNumber: String?,
)