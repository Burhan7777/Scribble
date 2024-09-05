package com.pzbdownloaders.scribble.edit_note_feature.domain.usecase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

fun checkIfUserHasCreatedPassword(): MutableLiveData<Boolean>{
    var result = MutableLiveData<Boolean>()
    var firestore = Firebase.firestore
    val scope = CoroutineScope(Dispatchers.IO)
    val document = firestore.collection("Passwords").document(Firebase.auth.currentUser?.uid!!)
    document.get().addOnSuccessListener {
        result.value = it.exists()
    }

    return result
}