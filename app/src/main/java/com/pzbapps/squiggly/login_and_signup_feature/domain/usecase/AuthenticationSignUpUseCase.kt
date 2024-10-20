package com.pzbapps.squiggly.login_and_signup_feature.domain.usecase

import android.util.Patterns

class AuthenticationSignUpUseCase(
) {
    fun checkAuth(
        email: String,
        password: String,
        repeatPassword: String,
        isChecked: Boolean
    ): String {
        if (email.isEmpty())
            return "Enter email"

        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches())
            return "This is not a valid email"

        if (password.isEmpty())
            return "Enter password"

        if (repeatPassword.isEmpty())
            return "Enter password to match"

        if (password.trim() != repeatPassword.trim()) {
            return "Passwords do not match"
        }

        if(!isChecked)
            return " Please accept terms and conditions"

        return "Correct"
    }

}