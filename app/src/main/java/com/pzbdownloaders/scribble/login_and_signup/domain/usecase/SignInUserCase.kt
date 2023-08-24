package com.pzbdownloaders.scribble.login_and_signup.domain.usecase

import android.provider.ContactsContract.Contacts
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.pzbdownloaders.scribble.common.presentation.Constant

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