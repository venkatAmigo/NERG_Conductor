package com.example.nergconductor.model

import kotlinx.serialization.Serializable

@Serializable
data class Reservation(
    val id: Int,
    val trainNumber: String,
    val date: String,
    val fromStation: String,
    val toStation:String,
    val railroadCars: List<RailroadCar>
)

@Serializable
data class RailroadCar(
    val id: Int,
    val railroadCarNumber: Int,
    val reservedSeats: Int
)
