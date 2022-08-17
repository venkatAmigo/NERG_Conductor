package com.example.nergpassenger.model

import com.example.nergconductor.model.Connection
import kotlinx.serialization.Serializable

@Serializable
data class LineDetail(
    val id: String,
    val name: String,
    val connections: List<Connection>
)
