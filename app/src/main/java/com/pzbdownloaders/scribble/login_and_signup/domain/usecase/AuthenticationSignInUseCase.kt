package com.pzbdownloaders.scribble.login_and_signup.domain.usecase

import android.util.Patterns

class AuthenticationSignInUseCase {

    fun authenticateSignIn(email: String, password: String): String {
        if (email.trim().isEmpty())
            return "Please enter email"

        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches())
            return "Email has incorrect format"

        if (password.trim().isEmpty()) {
            return "Please enter password"
        }
        return "Correct"
    }
}