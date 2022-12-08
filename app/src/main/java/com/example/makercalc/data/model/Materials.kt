package com.example.makercalc.data.model

import com.google.firebase.firestore.DocumentId

data class Materials(

    @DocumentId
    var materialID: String = "",
    var materialName: String = "",
    var materialPriceItem: Double = 0.00,
    var materialweight: Double = 0.00, // Gram
    var materialQuantity: Int = 1,
)
