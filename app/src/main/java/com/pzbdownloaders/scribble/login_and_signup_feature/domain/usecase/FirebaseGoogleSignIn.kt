package com.pzbdownloaders.scribble.login_and_signup_feature.domain.usecase

import android.content.Context
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pzbdownloaders.scribble.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 fun googleTapIn(context: Context) {

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.default_web_client_id))
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()


    var coroutineScope = CoroutineScope(Dispatchers.Main)
    var credentialManager = CredentialManager.create(context)
    coroutineScope.launch {
        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )


            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(result.credential.data)

            val googleIdToken = googleIdTokenCredential.idToken

            var googleAuthProvider = GoogleAuthProvider.getCredential(googleIdToken, null)


            var firebase = Firebase.auth
            firebase.signInWithCredential(googleAuthProvider).addOnSuccessListener {
                Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show()
            }

            // Handle successful sign-in
//        } catch (e: GetCredentialException) {
//            Toast.makeText(requireContext(), "credential", Toast.LENGTH_SHORT).show()
//        } catch (e: GoogleIdTokenParsingException) {
//            // Handle GoogleIdTokenParsingException thrown by `GoogleIdTokenCredential.createFrom()`
//            Toast.makeText(requireContext(), "google token", Toast.LENGTH_SHORT).show()
//        } catch (e: RestException) {
//            // Handle RestException thrown by Supabase
//            Toast.makeText(requireContext(), "rest", Toast.LENGTH_SHORT).show()
//        } catch (e: Exception) {
//            // Handle unknown exceptions
//            Toast.makeText(requireContext(), "exception", Toast.LENGTH_SHORT).show()
//        }
        } catch (exception: GetCredentialException) {

        }
    }
}