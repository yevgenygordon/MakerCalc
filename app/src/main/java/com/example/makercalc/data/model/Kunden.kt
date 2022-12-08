package com.example.makercalc.data.model


import com.google.firebase.firestore.DocumentId


data class Kunden(


    @DocumentId
    var kundenID: String = "",
    val userID: String = "",
    val kundenName: String = "",
    val kundenEmail: String = "",
    val kundenImage: String = "",

    val kundenAdress: String = "",
    val kundenCity: String = "",
    val kundenPostcode: String = "",
    val kundenTelNumber: String = "",




)
