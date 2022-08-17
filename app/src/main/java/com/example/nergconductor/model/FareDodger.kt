package com.example.nergconductor.model

import kotlinx.serialization.Serializable

//"id": 5,
//    "date": "2022-05-26",
//    "passengerCountWithTicket": 540,
//    "passengerCountWithoutTicket": 162
@Serializable
data class FareDodger(
    val id: Int,
    val date:String,
    val passengerCountWithTicket: Int,
    val passengerCountWithoutTicket:Int
)
