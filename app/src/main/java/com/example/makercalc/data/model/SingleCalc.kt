package com.example.makercalc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId



data class SingleCalc(


    @DocumentId
    var singleCalcID: String = "",
    var singleCalcName: String = "",
    var singleCalcDescription: String = "",
    var themeID: String = "",
    var image: String = "",

    var isTemplate:Int =1,
    var isFavorite:Int = 0,


    var prototype: Int = 0,
    var difficultyFactor: Double = 1.0,
    var quantity: Int = 1,

    var workTime: Double = 0.0,  // Std
    var workPriceHour: Double = 0.0,
    var workExtraPrice: Double = 0.0,
    var postProcessingPrice: Double = 0.0,
    var modelingCost: Double = 0.0,

    var electricityPrice: Double = 0.5,  //($/1000 Watt)






)