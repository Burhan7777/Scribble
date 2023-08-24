package com.pzbdownloaders.scribble.login_and_signup_feature.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.pzbdownloaders.scribble.common.presentation.Constant

class SignUpUserCase {

    fun signUpUser(email: String, password: String): MutableLiveData<String> {
        val result = MutableLiveData<String>()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.value = Constant.SUCCESS
                } else {
                    result.value = Constant.FAILURE
                }
            }
            .addOnFailureListener {
                result.value = it.toString()
            }
        return result

    }
}