package com.example.makercalc

import android.app.Application
import android.net.Uri

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.makercalc.data.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


const val TAG = "MainViewModel"

/**
 * Das MainViewModel kümmert sich um die Kommunikation mit der Firebase Authentication
 * um einen SHA-1 Key zu generieren einfach folgene Zeilen ins Terminal kopieren
 * >>keytool -alias androiddebugkey -keystore ~/.android/debug.keystore -list -v -storepass android<<
 *
 * keytool -alias MakerCalc -keystore ~/.android/debug.keystore -list -v -storepass android
 *
 * ACHTUNG: in der Firestore Datenbank folgende Regel festlegen
 * >> allow read, write: if request.auth != null; <<
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Kommunikationspunkt mit Firebase Storage
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    // Kommunikationspunkt mit der Firestore Datenbank
    private val db = FirebaseFirestore.getInstance()

    // Kommunikationspunkt mit der FirebaseAuth
    private val firebaseAuth = FirebaseAuth.getInstance()

    // currentuser ist null wenn niemand eingeloggt ist
    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    // User enthält alle relevanten Daten aus dem Firestore
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    // Kunden enthält alle relevanten Daten aus dem Firestore Kunden
    private val _kunden = MutableLiveData<List<Kunden>>()
    val kunden: LiveData<List<Kunden>>
        get() = _kunden

    // Kunde enthält alle relevanten Daten aus dem Firestore Kunden Dokument
    private val _kunde = MutableLiveData<Kunden>()
    val kunde: LiveData<Kunden>
        get() = _kunde


    // Calcs enthält alle relevanten Daten aus dem Firestore User \ SingleCalc
    private val _calcs = MutableLiveData<List<SingleCalc>>()
    val calcs: LiveData<List<SingleCalc>>
        get() = _calcs

    // Kunde enthält alle relevanten Daten aus dem Firestore Kunden Dokument
    private val _calc = MutableLiveData<SingleCalc>()
    val calc: LiveData<SingleCalc>
        get() = _calc

    // Kunde enthält alle relevanten Daten aus dem Firestore Template SingleCalc Dokument
    private val _tempcalc = MutableLiveData<SingleCalc>()
    val tempcalc: LiveData<SingleCalc>
        get() = _tempcalc


    // Kunde enthält alle relevanten Daten aus dem Firestore Template SingleCalc Dokument
    private val _templistencalc = MutableLiveData<ListenCalc>()
    val templistencalc: LiveData<ListenCalc>
        get() = _templistencalc

    // Calcs enthält alle relevanten Daten aus dem Firestore User \ SingleCalc \ Materials
    private val _listenCalcDetails = MutableLiveData<List<Materials>>()
    val listenCalcDetails: LiveData<List<Materials>>
        get() = _listenCalcDetails

    // Calcs enthält alle relevanten Daten aus dem Firestore User \ SingleCalc \ Materials
    private val _templistenCalcDetails = MutableLiveData<List<Detail>>()
    val templistenCalcDetails: LiveData<List<Detail>>
        get() = _templistenCalcDetails


    // Calcs enthält alle relevanten Daten aus dem Firestore User \ SingleCalc \ Materials
    private val _materials = MutableLiveData<List<Materials>>()
    val materials: LiveData<List<Materials>>
        get() = _materials


    // Calc enthält alle relevanten Daten aus dem Firestore  Template SingleCalc\ Materials
    private val _tempmaterials = MutableLiveData<List<Materials>>()
    val tempmaterials: LiveData<List<Materials>>
        get() = _tempmaterials

    // Calcs enthält alle relevanten Daten aus dem Firestore User \ SingleCalc \ Devices
    private val _devices = MutableLiveData<List<Devices>>()
    val devices: LiveData<List<Devices>>
        get() = _devices

    // Calc enthält alle relevanten Daten aus dem Firestore  Template SingleCalc\ Materials
    private val _tempdevices = MutableLiveData<List<Devices>>()
    val tempdevices: LiveData<List<Devices>>
        get() = _tempdevices


    // Calc enthält alle relevanten Daten aus dem Firestore  Template Themen\
    private val _themenliste = MutableLiveData<List<Themen>>()
    val themenliste: LiveData<List<Themen>>
        get() = _themenliste

    // Filter Live Data
    private val _sortBy = MutableLiveData<Sortby>(Sortby(false, false))
    val sortBy: LiveData<Sortby>
        get() = _sortBy

    private var _image = String()
    var image: String = ""
        get() = _image


    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    private val _isTemp = MutableLiveData<Boolean>(false)
    val isTemp: LiveData<Boolean>
        get() = _isTemp

    // Calc enthält alle relevanten Daten aus dem Firestore Materials Dokuments
    private val _materialsCost = MutableLiveData<Double>()
    val materialsCost: LiveData<Double>
        get() = _materialsCost

    // Calc enthält alle relevanten Daten aus dem Firestore Devices Dokuments
    private val _devicesCost = MutableLiveData<Double>()
    val devicesCost: LiveData<Double>
        get() = _devicesCost

    // Calc enthält Summe prodCostOf1Part
    private val _prodCostOf1Part = MutableLiveData<Double>()
    val prodCostOf1Part: LiveData<Double>
        get() = _prodCostOf1Part






    val delKunde = { kunde: Kunden ->
        Unit
        deleteKunde(kunde)
    }

    val saveKunde = { kunde: Kunden ->
        Unit
        saveKunde(kunde)
    }

    fun setTemp() {
        _isTemp.value = true
    }

    fun unsetTemp() {
        _isTemp.value = false
    }


    // Upload ProfilImage
    fun uploadImage(uri: Uri) {
        val imageRef = storageRef.child("images/${currentUser.value?.uid}/profilePic")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnFailureListener {
            Log.e("MainViewModel", "upload failed: $it")
        }

        uploadTask.addOnSuccessListener {
            Log.e("MainViewModel", "upload worked")
        }

        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    setImage(it.result)
                }
            }
        }
    }

    private fun setImage(uri: Uri) {
        db.collection("User").document(currentUser.value!!.uid)
            .update("userImage", uri.toString())
            .addOnFailureListener {
                Log.w(TAG, "Error writing document: $it")
                _toast.value = "error creating player\n${it.localizedMessage}"
                _toast.value = null
            }
            .addOnCompleteListener {
                getUserData()
            }
    }

    // Upload CalcImage  -------------------------------------

    fun uploadCalcImage(uri: Uri) {
        val imageRef = storageRef.child(
            "images/${currentUser.value?.uid}/" +
                    "${calc.value?.singleCalcID}"
        )
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnFailureListener {
            Log.e("MainViewModel", "upload failed: $it")
        }

        uploadTask.addOnSuccessListener {
            Log.e("MainViewModel", "upload worked")
        }

        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    _image = it.result.toString()
                    setCalcImage(it.result)
                }
            }
        }
    }

    private fun setCalcImage(uri: Uri) {
        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(calc.value!!.singleCalcID)
            .update("image", uri.toString())
            .addOnFailureListener {
                Log.w(TAG, "Error writing document: $it")
                _toast.value = "error creating player\n${it.localizedMessage}"
                _toast.value = null
            }
            .addOnCompleteListener {
                getCalc(calc.value!!.singleCalcID)
            }
    }


    // -------------------CALC Funktionen----------------------


    //  Get CalcID
    fun getTempCalcID() {

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc")
            .whereEqualTo("isTemplate", 1)
            .get()
            .addOnSuccessListener { result ->

                val outputcalcs = mutableListOf<SingleCalc>()

                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputcalcs.add(document.toObject(SingleCalc::class.java))

                }
                _calcs.value = outputcalcs
                _calc.value?.singleCalcID = calcs.value?.get(0)?.singleCalcID.toString()

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }


    // Delete Calc , in der Firestore Datenbank gelöscht
    fun deleteCalc(calc: String) {

        var delCalc = calc


        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(delCalc)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                getCalcs()

            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }


    // Duplicate Calc, in die Firestore Datenbank gespeichert
    fun copyCalc(newSingleCalc: SingleCalc) {


        val newSingleCalc = hashMapOf(

            "singleCalcName" to "${newSingleCalc.singleCalcName} Copy",
            "singleCalcDescription" to newSingleCalc.singleCalcDescription,
            "image" to newSingleCalc.image,

            "prototype" to newSingleCalc.prototype,
            "difficultyFactor" to newSingleCalc.difficultyFactor,
            "quantity" to newSingleCalc.quantity,

            "workTime" to newSingleCalc.workTime,  // Std
            "workPriceHour" to newSingleCalc.workPriceHour,
            "workExtraPrice" to newSingleCalc.workExtraPrice,
            "postProcessingPrice" to newSingleCalc.postProcessingPrice,
            "modelingCost" to newSingleCalc.modelingCost,
            "electricityPrice" to newSingleCalc.electricityPrice,  //($/1000 Watt)
            "isTemplate" to newSingleCalc.isTemplate,
            "isFavorite" to newSingleCalc.isFavorite,
            "themeID" to newSingleCalc.themeID,
        )

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document()
            .set(newSingleCalc)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                getCalcs()


            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }


    // Aktualisiere Calcsliste
    fun getCalcs() {


        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc")
            .get()
            .addOnSuccessListener { result ->

                val outputcalcs = mutableListOf<SingleCalc>()

                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputcalcs.add(document.toObject(SingleCalc::class.java))

                }
                _calcs.value = outputcalcs
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


    // Aktualisiere Themen
    fun getTheme() {

        db.collection("Themen")
            .get()
            .addOnSuccessListener { result ->

                var outputThemen = mutableListOf<Themen>()

                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputThemen.add(document.toObject(Themen::class.java))

                }
                _themenliste.value = outputThemen
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }


    // Aktualisiere Calc
    fun getCalc(id: String) {
        var singleCalcID: String = id

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(singleCalcID)
            .get()
            .addOnSuccessListener {
                _calc.value = it.toObject(SingleCalc::class.java)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(singleCalcID)
            .collection("Materials")
            .get()
            .addOnSuccessListener { result ->
                val outputMaterials = mutableListOf<Materials>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputMaterials.add(document.toObject(Materials::class.java))
                }
                _materials.value = outputMaterials


            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(singleCalcID)
            .collection("Devices")
            .get()
            .addOnSuccessListener { result ->
                val outputDevices = mutableListOf<Devices>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputDevices.add(document.toObject(Devices::class.java))
                }
                _devices.value = outputDevices

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }


    fun getMaterials() {

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(_calc.value!!.singleCalcID)
            .collection("Materials")
            .get()
            .addOnSuccessListener { result ->
                val outputMaterials = mutableListOf<Materials>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputMaterials.add(document.toObject(Materials::class.java))
                }
                _materials.value = outputMaterials
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }

    fun getDevices() {


        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(_calc.value!!.singleCalcID)
            .collection("Devices")
            .get()
            .addOnSuccessListener { result ->
                val outputDevices = mutableListOf<Devices>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputDevices.add(document.toObject(Devices::class.java))
                }
                _devices.value = outputDevices
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


    }


    // Erstelt neuer Calc von Template collection
    fun getTemplateCalc() {

        var singleCalcID: String = "singleCalcID"       //  Theme / Typ

        db.collection("SingleCalc").document(singleCalcID)
            .get().addOnSuccessListener {
                _calc.value = it.toObject(SingleCalc::class.java)
                _calc.value?.let { it1 -> copyCalc(it1) }
                getTempCalcID()

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


    }

    fun getTemplateMaterial() {

        var singleCalcID: String = "singleCalcID"       //  Theme / Typ

        db.collection("SingleCalc").document(singleCalcID)
            .collection("Materials")
            .get()
            .addOnSuccessListener { result ->
                val outputMaterials = mutableListOf<Materials>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputMaterials.add(document.toObject(Materials::class.java))
                }
                _materials.value = outputMaterials
                copyNewMaterial()

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


    fun getTemplateDevice() {

        var singleCalcID: String = "singleCalcID"       //  Theme / Typ

        db.collection("SingleCalc").document(singleCalcID)
            .collection("Devices")
            .get()
            .addOnSuccessListener { result ->
                val outputDevices = mutableListOf<Devices>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputDevices.add(document.toObject(Devices::class.java))
                }
                _devices.value = outputDevices
                copyNewDevices()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


    private fun copyNewMaterial() {

        for (i in _materials.value?.indices!!) {

            val material = hashMapOf(
                "materialName" to _materials.value!![i].materialName,
                "materialPriceItem" to _materials.value!![i].materialPriceItem,
                "materialweight" to _materials.value!![i].materialweight,
                "materialQuantity" to _materials.value!![i].materialQuantity,
            )

            db.collection("User").document(currentUser.value!!.uid)
                .collection("SingleCalc").document(_calc.value!!.singleCalcID)
                .collection("Materials").document()
                .set(material)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }

    fun updateMaterial(editMaterial: Materials) {

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(_calc.value!!.singleCalcID)
            .collection("Materials").document(editMaterial.materialID)
            .update(

                "materialName",
                editMaterial.materialName,
                "materialPriceItem",
                editMaterial.materialPriceItem,
                "materialweight",
                editMaterial.materialweight,
                "materialQuantity",
                editMaterial.materialQuantity,
            )
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                getMaterials()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }

    fun deleteMaterial(editMaterial: Materials) {

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(_calc.value!!.singleCalcID)
            .collection("Materials").document(editMaterial.materialID)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                getMaterials()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

    }

    fun setNewMaterial(newSingleCalcMaterial: Materials) {


        val material = hashMapOf(
            "materialName" to newSingleCalcMaterial.materialName,
            "materialPriceItem" to newSingleCalcMaterial.materialPriceItem,
            "materialweight" to newSingleCalcMaterial.materialweight,
            "materialQuantity" to newSingleCalcMaterial.materialQuantity,
        )

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(_calc.value!!.singleCalcID)
            .collection("Materials").document()
            .set(material)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }


    private fun copyNewDevices() {

        for (i in _devices.value?.indices!!) {

            val device = hashMapOf(
                "deviceName" to _devices.value!![i].deviceName,
                "deviceTime" to _devices.value!![i].deviceTime,
                "devicePower" to _devices.value!![i].devicePower,
                "deviceAmortizationPrice" to _devices.value!![i].deviceAmortizationPrice,
            )

            db.collection("User").document(currentUser.value!!.uid)
                .collection("SingleCalc").document(_calc.value!!.singleCalcID)
                .collection("Devices").document()
                .set(device)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }

    fun updateDevices(editDevices: Devices) {

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(_calc.value!!.singleCalcID)
            .collection("Devices").document(editDevices.devicesID)
            .update(

                "deviceName",
                editDevices.deviceName,
                "devicePower",
                editDevices.devicePower,
                "deviceTime",
                editDevices.deviceTime,
                "deviceAmortizationPrice",
                editDevices.deviceAmortizationPrice,
            )
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                getDevices()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }

    fun deleteDevice(editDevice: Devices) {

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(_calc.value!!.singleCalcID)
            .collection("Devices").document(editDevice.devicesID)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                getDevices()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

    }

    fun setNewDevices(newSingleCalcDevices: Devices) {


        val device = hashMapOf(

            "deviceName" to newSingleCalcDevices.deviceName,
            "deviceTime" to newSingleCalcDevices.deviceTime,
            "devicePower" to newSingleCalcDevices.devicePower,
            "deviceAmortizationPrice" to newSingleCalcDevices.deviceAmortizationPrice,
        )

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(_calc.value!!.singleCalcID)
            .collection("Devices").document()
            .set(device)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }


    // Update SingleCalc in die Firestore Datenbank gespeichert
    fun updateSingleCalc(updateSingleCalc: SingleCalc) {


        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(updateSingleCalc.singleCalcID)
            .update(
                "singleCalcName",
                updateSingleCalc.singleCalcName,
                "singleCalcDescription",
                updateSingleCalc.singleCalcDescription,
                "image",
                updateSingleCalc.image,
                "prototype",
                updateSingleCalc.prototype,
                "difficultyFactor",
                updateSingleCalc.difficultyFactor,
                "quantity",
                updateSingleCalc.quantity,
                "workTime",
                updateSingleCalc.workTime,
                "workPriceHour",
                updateSingleCalc.workPriceHour,
                "workExtraPrice",
                updateSingleCalc.workExtraPrice,
                "postProcessingPrice",
                updateSingleCalc.postProcessingPrice,
                "modelingCost",
                updateSingleCalc.modelingCost,
                "electricityPrice",
                updateSingleCalc.electricityPrice,
                "isTemplate",
                updateSingleCalc.isTemplate,
                "isFavorite",
                updateSingleCalc.isFavorite,


                )
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")

                getCalc(updateSingleCalc.singleCalcID)
                getCalcs()


            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun updateIsTemplate() {
        _calc.value?.isTemplate = 0
    }


//----------  USER Funktionen   -----------------------------------


    // hier wird versucht einen User zu erstellen um diesen anschließend auch gleich
    // einzuloggen
    fun signUp(email: String, password: String, user: User) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        _currentUser.value = firebaseAuth.currentUser
                        setUserData(user)
                        _toast.value = "welcome"
                        _toast.value = null
                    } else {
                        Log.e(TAG, "Login failed: ${it.exception}")
                        _toast.value = "login failed\n${it.exception?.localizedMessage}"
                        _toast.value = null
                    }
                }
            } else {
                Log.e(TAG, "SignUp failed: ${it.exception}")
                _toast.value = "signup failed\n${it.exception?.localizedMessage}"
                _toast.value = null
            }
        }
    }


    // User Daten aktualisieren, in die Firestore Datenbank gespeichert

    fun updateUser() {


        db.collection("User").document(currentUser.value!!.uid)
            .update(
                "userName",
                user.value?.userName,
                "userEmail",
                user.value?.userEmail,
                "userImage",
                user.value?.userImage,
                "userFooter",
                user.value?.userFooter,
                "userAdress",
                user.value?.userAdress,
                "userAdressText",
                user.value?.userAdressText,
                "userCity",
                user.value?.userCity,
                "userPostcode",
                user.value?.userPostcode,
                "userTelNumber",
                user.value?.userTelNumber,


                )
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                getUserData()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }


    // hier wird userid, nickname und Password in die Firestore Datenbank gespeichert
    private fun setUserData(user: User) {
        db.collection("User").document(currentUser.value!!.uid)
            .set(user)
            .addOnFailureListener {
                Log.w(TAG, "Error writing document: $it")
                _toast.value = "error creating player\n${it.localizedMessage}"
                _toast.value = null
            }
    }


    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = firebaseAuth.currentUser
                _toast.value = "welcome"
                _toast.value = null
            } else {
                Log.e(TAG, "Login failed: ${it.exception}")
                _toast.value = "login failed\n${it.exception?.localizedMessage}"
                _toast.value = null
            }
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }

    // hier werden Spielerdaten mittles userid aus dem Firestore geladen
    fun getUserData() {
        db.collection("User").document(currentUser.value!!.uid)
            .get().addOnSuccessListener {
                _user.value = it.toObject(User::class.java)
            }
            .addOnFailureListener {
                Log.e(TAG, "Error reading document: $it")
            }

    }


    //-----------------CUSTOMER Funktionen

    // Delete Kunde , in der Firestore Datenbank gelöscht
    fun deleteKunde(kunde: Kunden) {
        kunde.kundenID
        db.collection("User").document(currentUser.value!!.uid)
            .collection("Kunden").document(kunde.kundenID)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                getKunden()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }


    // Aktualisiere Kundenliste
    fun getKunden() {

        db.collection("User").document(currentUser.value!!.uid)
            .collection("Kunden")
            .get()
            .addOnSuccessListener { result ->

                val output = mutableListOf<Kunden>()

                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    output.add(document.toObject(Kunden::class.java))

                }
                _kunden.value = output
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


    // Update Kundedaten, in die Firestore Datenbank gespeichert
    fun saveKunde(editKunde: Kunden) {

        var upgradekunde = editKunde

        db.collection("User").document(currentUser.value!!.uid)
            .collection("Kunden").document(upgradekunde.kundenID)
            .update(
                "kundenName",
                upgradekunde.kundenName,
                "kundenAdress",
                upgradekunde.kundenAdress,
                "kundenCity",
                upgradekunde.kundenCity,
                "kundenPostcode",
                upgradekunde.kundenPostcode
            )
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                getKunden()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }


    // Kunde hinzufügen, in die Firestore Datenbank gespeichert
    fun updateKunde(kunde: Kunden) {

        val kunde = hashMapOf(

            "kundenName" to kunde.kundenName,
            "kundenEmail" to "",
            "kundenImage" to "",

            "kundenAdress" to kunde.kundenAdress,
            "kundenCity" to kunde.kundenCity,
            "kundenPostcode" to kunde.kundenPostcode,
            "kundenTelNumber" to "",

            )

        db.collection("User").document(currentUser.value!!.uid)
            .collection("Kunden").document()
            .set(kunde)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


    }


    // Filter und Sort by -  Funktionen


    // Favorite Status ändern, in die Firestore Datenbank gespeichert
    fun updateFavorite(id: String, isFav: Int) {

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(id)
            .update(
                "isFavorite",
                isFav,
            )
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                // getCalcs()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }


    fun filterFavorite() {

        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc")
            .whereEqualTo("isFavorite", 1)
            .get()
            .addOnSuccessListener { result ->

                val outputcalcs = mutableListOf<SingleCalc>()

                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputcalcs.add(document.toObject(SingleCalc::class.java))

                }
                _calcs.value = outputcalcs

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }

    fun sortByName() {
        var calcByName = db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc")

        calcByName
            .orderBy("singleCalcName")
            .get()
            .addOnSuccessListener { result ->

                val outputcalcs = mutableListOf<SingleCalc>()

                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputcalcs.add(document.toObject(SingleCalc::class.java))

                }
                _calcs.value = outputcalcs
                (_calcs.value as MutableList<SingleCalc>).sortedBy { singleCalc: SingleCalc ->
                    singleCalc.singleCalcName
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }


    //----------------------Profit Rechnen-------------------------


    fun getMatDevCostOfProd1(id: String) {
        getMaterialCost(id)
        getDeviceCost(id)
        _prodCostOf1Part.value = _devicesCost.value?.plus(_materialsCost.value!!)

    }


    fun getMaterialCost(id: String) {
        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(id)
            .collection("Materials")
            .get()
            .addOnSuccessListener { result ->
                var materialsCost = 0.0
                val outputMaterials = mutableListOf<Materials>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputMaterials.add(document.toObject(Materials::class.java))
                }

                for (i in 0..outputMaterials.size - 1) {
                    var mCost = (outputMaterials[i].materialweight / 1000) *
                            outputMaterials[i].materialQuantity.toDouble() *
                            outputMaterials[i].materialPriceItem
                    materialsCost += mCost

                }
                _materialsCost.value = materialsCost
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }

    fun getDeviceCost(id: String) {
        db.collection("User").document(currentUser.value!!.uid)
            .collection("SingleCalc").document(id)
            .collection("Devices")
            .get()
            .addOnSuccessListener { result ->
                var devicesCost = 0.0
                val outputDevices = mutableListOf<Devices>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    outputDevices.add(document.toObject(Devices::class.java))
                }


                for (i in 0..outputDevices.size - 1) {
                    var dCost = ((outputDevices[i].deviceTime / 60) *
                            outputDevices[i].deviceAmortizationPrice) +
                            ((outputDevices[i].deviceTime / 60) * (outputDevices[i].devicePower / 1000) * 0.45)

                    devicesCost += dCost

                }
                _devicesCost.value = devicesCost
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }


}
