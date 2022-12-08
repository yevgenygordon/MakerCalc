package com.example.makercalc.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId


@Entity
data class User(



    @DocumentId
    val userID: String = "",
    var userName: String = "",
    var userEmail: String = "",
    var userPassword: String= "",
    var userImage: String = "",
    var userFooter: String = "",

    var userAdress: String = "",
    var userAdressText: String = "",
    var userCity: String = "",
    var userPostcode: String = "",
    var userTelNumber: String = "",

    var userCalcs: MutableList<String>  = mutableListOf(),       // singleCalcID


)


