package com.pzbapps.squiggly.main_screen.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.roundToInt

fun getRemainingDaysFromFirebase(): MutableLiveData<String> {
    val remainingDays = MutableLiveData<String>()

    val firestore = Firebase.firestore
    if (Firebase.auth.currentUser?.uid != null) {
        var document = firestore.collection("Users").document(Firebase.auth.currentUser?.uid!!)
        document.get().addOnSuccessListener {
            if (it.exists()) {
                var details = it.data
                val calendar = Calendar.getInstance()
                var timeInSecs = calendar.timeInMillis / 1000L
                val dateFormatter = SimpleDateFormat("EEEE, MMMM d, yyyy HH:mm")
                val endingTime: String = dateFormatter.format(Date(timeInSecs * 1000L))
                var remainingTIme = details!!["remainingTime"] as Long
                var remainingWaqt = remainingTIme - timeInSecs
                remainingDays.value = (remainingWaqt * 0.000011574).roundToInt().toString()
            }
        }
    }
    return remainingDays
}