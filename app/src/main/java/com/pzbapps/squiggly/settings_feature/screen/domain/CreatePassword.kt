package com.pzbapps.squiggly.settings_feature.screen.domain

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbapps.squiggly.common.domain.utils.Constant

fun checkPasswordIfMatches(password: String, confirmPassword: String): Boolean {
    return password.trim() == confirmPassword.trim()
}

fun createPassword(password: String): MutableLiveData<String> {
    var result = MutableLiveData<String>()
    val firestore = Firebase.firestore
    val document = firestore.collection("Passwords").document(Firebase.auth.currentUser?.uid!!)
    val map: HashMap<String, Any> = hashMapOf()
    map["password"] = password.trim()
    map["userId"] = Firebase.auth.currentUser?.uid!!
    document.set(map).addOnSuccessListener {
        result.value = Constant.SUCCESS
    }.addOnFailureListener {
        result.value = Constant.FAILURE
    }
    return result
}