package com.pzbdownloaders.scribble.login_and_signup_feature.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.common.domain.utils.Constant
import com.pzbdownloaders.scribble.common.presentation.*
import com.pzbdownloaders.scribble.login_and_signup_feature.presentation.components.AlertBoxWhyLogIn
import googleSignInButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity
) {

    var showGoogleSignIn = remember { mutableStateOf(false) }
    var showWhyLoginDialogBox = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        if (showWhyLoginDialogBox.value) {
            AlertBoxWhyLogIn {
                showWhyLoginDialogBox.value = false
            }
        }
        var email by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }

        var passwordVisibilityToggle by remember {
            mutableStateOf(false)
        }

        var loginButtonClick by remember {
            mutableStateOf(false)
        }


        var context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            IconButton(onClick = { navHostController.navigateUp() }) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.ArrowBackIos,
                    contentDescription = "Back to main screen",
                    tint = androidx.compose.material.MaterialTheme.colors.onPrimary
                )
            }
            Text(
                text = "Login",
                fontSize = 55.sp,
                fontFamily = FontFamily.fontFamilyExtraLight,
                modifier = Modifier.padding(20.dp),
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = "but why ?",
                fontFamily = FontFamily.fontFamilyBold,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.clickable {
                    showWhyLoginDialogBox.value = true
                },
                color = MaterialTheme.colors.onPrimary
            )
        }
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colors.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colors.onPrimary,
                focusedTextColor = MaterialTheme.colors.onPrimary,
                unfocusedTextColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                focusedContainerColor = MaterialTheme.colors.primary,
                unfocusedContainerColor = MaterialTheme.colors.primary,
            ),
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily.fontFamilyRegular
            ),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Email",
                    color = MaterialTheme.colors.onPrimary,
                    fontFamily = FontFamily.fontFamilyLight
                )
            },
            shape = MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(15.dp),
                topEnd = CornerSize(15.dp),
                bottomStart = CornerSize(15.dp),
                bottomEnd = CornerSize(15.dp),
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colors.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colors.onPrimary,
                focusedTextColor = MaterialTheme.colors.onPrimary,
                unfocusedTextColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                focusedContainerColor = MaterialTheme.colors.primary,
                unfocusedContainerColor = MaterialTheme.colors.primary,
            ),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily.fontFamilyRegular
            ),
            visualTransformation = if (!passwordVisibilityToggle) PasswordVisualTransformation() else VisualTransformation.None,
            maxLines = 1,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Password",
                    color = MaterialTheme.colors.onPrimary,
                    fontFamily = FontFamily.fontFamilyLight
                )
            },
            shape = MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(15.dp),
                topEnd = CornerSize(15.dp),
                bottomStart = CornerSize(15.dp),
                bottomEnd = CornerSize(15.dp),
            ),
            trailingIcon = {
                if (!passwordVisibilityToggle)
                    IconButton(onClick = { passwordVisibilityToggle = !passwordVisibilityToggle }) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Visibility",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    } else {
                    IconButton(onClick = { passwordVisibilityToggle = !passwordVisibilityToggle }) {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = "Visibility",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        )

        Button(
            onClick = {
                val result = viewModel.authenticationSignIn(email, password)
                if (result == "Correct") {
                    loginButtonClick = !loginButtonClick
                    viewModel.signInUser(email, password)
                    viewModel.getResultFromSignIn.observe(activity) {
                        when (it) {
                            Constant.SUCCESS -> {
                                Toast.makeText(
                                    context,
                                    "Sign in successful",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val sharedPreferences = context.getSharedPreferences(
                                    Constant.SHARED_PREP_NAME,
                                    Context.MODE_PRIVATE
                                )
                                val editor = sharedPreferences.edit()
                                editor.apply {
                                    putString(Constant.USER_KEY, Constant.USER_VALUE)
                                }.apply()

                                navHostController.popBackStack()
                                navHostController.navigate(Screens.HomeScreen.route)
                            }

                            Constant.FAILURE -> {
                                loginButtonClick = !loginButtonClick
                                Toast.makeText(
                                    context,
                                    "Authentication failed",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(60.dp)
            /*   .border(
                   border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
                   MaterialTheme.shapes.medium.copy(
                       topStart = CornerSize(15.dp),
                       topEnd = CornerSize(15.dp),
                       bottomEnd = CornerSize(15.dp),
                       bottomStart = CornerSize(15.dp),
                   )
               )*/,
            shape = MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(15.dp),
                topEnd = CornerSize(15.dp),
                bottomStart = CornerSize(15.dp),
                bottomEnd = CornerSize(15.dp),
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.onPrimary
            )
        ) {
            Text(
                text = if (!loginButtonClick) "Log In to your account" else "Logging in...",
                fontSize = 15.sp,
                color = MaterialTheme.colors.onSecondary,
                fontFamily = FontFamily.fontFamilyRegular
            )
            if (loginButtonClick) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(20.dp)
                        .height(20.dp),
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }

        /*     Text(
             text = "-------------------------------------- or -------------------------------------",
             color = MaterialTheme.colors.onPrimary,
             fontSize = 15.sp
         )*/
        /*  Button(
          onClick = { *//*TODO*//* },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = googleColor
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Log in via Google",
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily.fontFamilyLight
            )
        }*/
//        Box(modifier = Modifier.fillMaxWidth()) {
//            androidx.compose.material3.TextButton(
//                modifier = Modifier.align(Alignment.CenterEnd),
//                onClick = {
//
//                }) {
//                Text(
//                    "Forgot Password?",
//                    fontFamily = FontFamily.fontFamilyBold,
//                    fontStyle = FontStyle.Italic,
//                    color = MaterialTheme.colors.onPrimary,
//                    fontSize = 12.sp,
//                    textDecoration = TextDecoration.Underline,
//
//                    )
//            }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Not a user yet?",
                color = MaterialTheme.colors.onPrimary,
                fontSize = 15.sp,
                fontFamily = FontFamily.fontFamilyRegular
            )
            TextButton(onClick = { navHostController.navigate(Screens.SignUpScreen.route) }) {
                Text(
                    text = "REGISTER",
                    color = MaterialTheme.colors.onPrimary,
                    fontFamily = FontFamily.fontFamilyBold,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 15.sp
                )

            }
        }

        var login = "Sign in with Google"
        var signingIn = "Signing in..."
        var clicked by remember {
            mutableStateOf(false)
        }
        val rotate by animateFloatAsState(if (clicked) 360f else 0f, label = "")
        val rotateAnimationByDuration by animateFloatAsState(
            targetValue = rotate,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing,
            ), label = ""
        )
        androidx.compose.material3.Card(
            modifier = Modifier
                .height(100.dp)
                .padding(8.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ),
            shape = RoundedCornerShape(7.dp),
            onClick = {
                clicked = !clicked
                //showGoogleSignIn.value = true
                googleSignInButton(navHostController, context)
            },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colors.onPrimary
            )
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painterResource(id = R.drawable.google),
                    contentDescription = "Google Icon",
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp,
                        )
                        .rotate(rotateAnimationByDuration)

                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (clicked) signingIn else login,
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.padding(
                        end = 12.dp
                    ),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.fontFamilyRegular
                )
                if (clicked) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(15.dp)
                            .height(15.dp),
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    }
}




