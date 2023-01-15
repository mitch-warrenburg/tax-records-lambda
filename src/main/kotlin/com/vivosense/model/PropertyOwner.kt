package com.vivosense.model

import kotlinx.serialization.Serializable

@Serializable
data class PropertyOwner(
    val lastName: String,
    val firstName: String,
    val mortgagee: Mortgagee?,
)
