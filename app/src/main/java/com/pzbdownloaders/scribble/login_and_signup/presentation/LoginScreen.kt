package com.pzbdownloaders.scribble.login_and_signup.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStoreFile
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.common.presentation.*
import com.pzbdownloaders.scribble.ui.theme.googleColor
import java.time.format.TextStyle

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: MainActivityViewModel,
    activity: MainActivity
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }

        var context = LocalContext.current
        Text(
            text = "Login",
            fontSize = 55.sp,
            fontFamily = FontFamily.fontFamilyExtraLight,
            modifier = Modifier.padding(20.dp),
            color = MaterialTheme.colors.onPrimary
        )
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
            )
        )

        Button(
            onClick = {
                val result = viewModel.authenticationSignIn(email, password)
                if (result == "Correct") {
                    viewModel.signInUser(email, password)
                    viewModel.getResultFromSignIn.observe(activity) {
                        when (it) {
                            Constant.SUCCESS -> {
                                Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT)
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
                                Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT)
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
                text = "Log In",
                fontSize = 15.sp,
                color = MaterialTheme.colors.onSecondary,
                fontFamily = FontFamily.fontFamilyLight
            )
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
        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "Not a user yet?", color = MaterialTheme.colors.onPrimary)
            TextButton(onClick = { navHostController.navigate(Screens.SignUpScreen.route) }) {
                Text(text = "REGISTER", color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}


