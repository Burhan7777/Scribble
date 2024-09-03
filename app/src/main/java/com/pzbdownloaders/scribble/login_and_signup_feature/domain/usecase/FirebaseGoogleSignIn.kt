//package com.pzbdownloaders.scribble.login_and_signup_feature.domain.usecase
//
//import android.widget.Toast
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//private fun googleTapIn(context:Context) {
//
//    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
//        .setFilterByAuthorizedAccounts(false)
//        .setServerClientId(getString(R.string.default_web_client_id))
//        .build()
//
//    val request: GetCredentialRequest = GetCredentialRequest.Builder()
//        .addCredentialOption(googleIdOption)
//        .build()
//
//
//    var coroutineScope = CoroutineScope(Dispatchers.Main)
//    var credentialManager = CredentialManager.create(requireContext())
//    coroutineScope.launch {
//        try {
//            val result = credentialManager.getCredential(
//                request = request,
//                context = requireContext(),
//            )
//
//
//            val googleIdTokenCredential = GoogleIdTokenCredential
//                .createFrom(result.credential.data)
//
//            val googleIdToken = googleIdTokenCredential.idToken
//
//            var googleAuthProvider = GoogleAuthProvider.getCredential(googleIdToken, null)
//
//
//            var firebase = Firebase.auth
//            firebase.signInWithCredential(googleAuthProvider).addOnSuccessListener {
//                activateTrial(
//                    requireActivity(),
//                    it.user?.uid,
//                    it.user?.email,
//                    findNavController()
//                )
//            }
//
//            // Handle successful sign-in
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
//    }
//
//}