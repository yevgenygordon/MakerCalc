package com.example.makercalc.data.model

import com.google.firebase.firestore.DocumentId



data class Detail(


    @DocumentId
    var detailID: String = "",
    var detailTitle: String = "",
    var detailQuantity: String = "",
    var detailPrice: String = "",
    var detailMass: String = "",


)