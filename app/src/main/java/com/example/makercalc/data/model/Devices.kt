package com.example.makercalc.data.model

import com.google.firebase.firestore.DocumentId

data class Devices(

    @DocumentId
    var devicesID: String = "",
    var deviceName: String = "",
    var deviceTime: Int = 0, // Min
    var devicePower: Int = 0,   // Watt
    var deviceAmortizationPrice: Double = 0.0,
)
