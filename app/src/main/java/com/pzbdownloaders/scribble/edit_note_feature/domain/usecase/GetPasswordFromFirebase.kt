package com.pzbdownloaders.scribble.edit_note_feature.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.common.domain.utils.Constant

fun getPasswordFromFirebase(): MutableLiveData<String> {
    var password = MutableLiveData<String>()
    val firestore = Firebase.firestore
    val document = firestore.collection("Passwords").document(Firebase.auth.currentUser?.uid!!)
    document.get().addOnSuccessListener {
        password.value = it.get("password") as String
    }.addOnFailureListener {
        password.value = Constant.FAILURE
    }
    return password
}