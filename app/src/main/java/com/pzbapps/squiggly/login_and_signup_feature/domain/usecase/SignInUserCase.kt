package com.pzbapps.squiggly.login_and_signup_feature.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.pzbapps.squiggly.common.domain.utils.Constant

class SignInUserCase {

    fun signInUser(email: String, password: String): MutableLiveData<String> {
        val result = MutableLiveData<String>()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.trim(), password.trim())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.value = Constant.SUCCESS
                } else {
                    result.value = Constant.FAILURE
                }
            }
        return result
    }
}