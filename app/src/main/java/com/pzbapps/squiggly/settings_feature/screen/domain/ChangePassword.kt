package com.pzbapps.squiggly.settings_feature.screen.domain

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun changePassword(password: String): MutableLiveData<Boolean> {
    val passwordChangedSuccessfully = MutableLiveData<Boolean>()
    val firestore = Firebase.firestore
    val document = firestore.collection("Passwords").document(Firebase.auth.currentUser?.uid!!)
    val hashmap: HashMap<String, Any> = hashMapOf()
    hashmap["password"] = password
    document.update(hashmap).addOnSuccessListener {
        passwordChangedSuccessfully.value = true
    }.addOnFailureListener {
        passwordChangedSuccessfully.value = false
    }

    return passwordChangedSuccessfully
}