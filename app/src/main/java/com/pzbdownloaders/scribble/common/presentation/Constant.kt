package com.pzbdownloaders.scribble.common.presentation

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

object Constant {

    val firebaseUser = FirebaseAuth.getInstance().currentUser?.uid

    const val SUCCESS = "SUCCESS"
    const val FAILURE = "FAILURE"
    const val USER_KEY = "LoggedInUser"
    const val USER_VALUE = "User"
    const val SHARED_PREP_NAME = "rememberUser"
    const val ARCHIVE = "archive"
    const val HOME = "home"
}