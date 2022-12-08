package com.example.makercalc.data.model

import com.google.firebase.firestore.DocumentId



data class ListenCalc(

    @DocumentId
    var listenCalcID: String = "",
    var listenCalcName: String = "",
    var listenCalcDescription: String = "",

)