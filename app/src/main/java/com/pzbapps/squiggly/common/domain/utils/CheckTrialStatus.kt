package com.pzbapps.squiggly.common.domain.utils
//
//import android.app.Activity
//import android.content.Context
//import androidx.lifecycle.MutableLiveData
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.firestore.SetOptions
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Date
//
//var trialPeriodExists = MutableLiveData<String>("No Value")
//fun checkTrialPeriod(activity: Activity) {
//    val firestore = Firebase.firestore
//    if (Firebase.auth.currentUser?.uid != null) {
//        var document = firestore.collection("Users").document(Firebase.auth.currentUser?.uid!!)
//        document.get().addOnSuccessListener {
//            if (it.exists()) {
//                var details = it.data
//                val calendar = Calendar.getInstance()
//                var timeInSecs = calendar.timeInMillis / 1000L
//                val dateFormatter = SimpleDateFormat("EEEE, MMMM d, yyyy HH:mm")
//                val endingTime: String = dateFormatter.format(Date(timeInSecs * 1000L))
//                var remainingTIme = details!!["remainingTime"] as Long
//                if (remainingTIme < timeInSecs) {
//                    var document =
//                        firestore.collection("Users").document(Firebase.auth.currentUser?.uid!!)
//                    var hashmap: HashMap<String, Any> = HashMap()
//                    hashmap["trialOngoing"] = false
//                    hashmap["dateWhenTrialPeriodEnded"] = endingTime
//                    document.set(hashmap, SetOptions.merge()).addOnSuccessListener {
//                        trialPeriodExists.value = Constant.TRIAL_ENDED
//                    }
//                } else {
//                    trialPeriodExists.value = Constant.TRIAL_ONGOING
//                }
//            }
//        }
//
//    }
//}
//
