package com.pzbdownloaders.scribble.login_and_signup.domain.usecase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.common.presentation.Constant
import com.pzbdownloaders.scribble.login_and_signup.sign_up.failureResultSigIn
import com.pzbdownloaders.scribble.login_and_signup.sign_up.successResultSignIn
import kotlinx.coroutines.tasks.await

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