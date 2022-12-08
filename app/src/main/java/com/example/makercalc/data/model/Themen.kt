package com.example.makercalc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId



data class Themen(


    @DocumentId
    var themeID: String = "",
    var themeTemplate: MutableList<String> = mutableListOf(),
    var themeImageResource: String = ""


)
