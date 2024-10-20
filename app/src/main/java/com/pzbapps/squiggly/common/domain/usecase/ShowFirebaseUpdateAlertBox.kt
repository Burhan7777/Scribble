package com.pzbapps.squiggly.common.domain.usecase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ShowFirebaseUpdateAlertBox(
    showUpdateDialogBox: MutableState<Boolean>,
    show: MutableState<Boolean>,
    title: MutableState<String>,
    body: MutableState<String>,
    versionCode: MutableState<String>,
    buttonName: MutableState<String>,
    code: Int
) {
    LaunchedEffect(Unit) {
        var firestore = Firebase.firestore
        var document = firestore.collection("AlertBoxes").document("updateApp")
        document.get().addOnSuccessListener {
            show.value = it.get("show") as Boolean
            title.value = it.get("title") as String
            body.value = it.get("body") as String
            versionCode.value = it.get("versioncode") as String
            buttonName.value = it.get("buttonName") as String
            if (show.value && code.toString().toInt() < versionCode.value.toInt()) {
                showUpdateDialogBox.value = true
            }
        }
    }
}