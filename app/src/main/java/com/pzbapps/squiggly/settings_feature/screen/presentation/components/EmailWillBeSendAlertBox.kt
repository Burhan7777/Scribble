package com.pzbapps.squiggly.settings_feature.screen.presentation.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaquo.python.Python
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pzbapps.squiggly.common.presentation.FontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun EmailWillBeSendAlertBox(
    showVerificationCodeAlertBox: MutableState<Boolean>,
    showEmailWillBeSendAlertBox: MutableState<Boolean>,
    showLoadingDialog: MutableState<Boolean>,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(15.dp),
            topEnd = CornerSize(15.dp),
            bottomStart = CornerSize(15.dp),
            bottomEnd = CornerSize(15.dp),
        ),
        containerColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
        title = {
            Text(
                text = "An email will be send to your email id with a reset code.",
                fontSize = 15.sp,
                fontFamily = FontFamily.fontFamilyRegular,
                color = MaterialTheme.colors.onPrimary
            )
        }, confirmButton = {
            androidx.compose.material.Button(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        showLoadingDialog.value = true

                        var userEmail = Firebase.auth.currentUser?.email
                        val python = Python.getInstance()
                        val module = python.getModule("sendPasswordResetEmail")
                        val resetCode = module.callAttr("send_reset_code", userEmail)
                        //showEmailWillBeSendAlertBox.value = false
                        onDismiss()

                        withContext(Dispatchers.Main) {
                            var firestore = Firebase.firestore
                            var document = firestore.collection("userResetCode")
                                .document(Firebase.auth.currentUser?.uid!!)
                            var hashMap = HashMap<String, Any>()
                            hashMap["Reset Code"] = resetCode.toString()
                            document.set(hashMap).addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "Verification code sent successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showLoadingDialog.value = false
                                showVerificationCodeAlertBox.value = true

                            }.addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "Failed to send verification code",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showLoadingDialog.value = false
                            }
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = androidx.compose.material.MaterialTheme.colors.onPrimary,
                    contentColor = androidx.compose.material.MaterialTheme.colors.primary
                ),
                shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                )
            ) {
                androidx.compose.material.Text(
                    text = "Proceed",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        },
        dismissButton = {
            androidx.compose.material.OutlinedButton(
                onClick = {
                    onDismiss()
                },
                shape = androidx.compose.material.MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(15.dp),
                    topEnd = CornerSize(15.dp),
                    bottomStart = CornerSize(15.dp),
                    bottomEnd = CornerSize(15.dp),
                ),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colors.onPrimary
                ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = androidx.compose.material.MaterialTheme.colors.primaryVariant,
                    contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary
                ),
            ) {
                androidx.compose.material.Text(
                    text = "Cancel",
                    fontFamily = FontFamily.fontFamilyRegular
                )
            }
        })
}
