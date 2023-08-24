package com.pzbdownloaders.scribble.login_and_signup.sign_up

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.presentation.*

@Composable
fun SignUpScreen(
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

        var name by remember {
            mutableStateOf("")
        }

        var lastName by remember {
            mutableStateOf("")
        }

        var repeatedPassword by remember {
            mutableStateOf("")
        }

        var isChecked by remember {
            mutableStateOf(false)
        }

        var context = LocalContext.current
        Text(
            text = "Sign Up",
            fontSize = 55.sp,
            fontFamily = FontFamily.fontFamilyExtraLight,
            modifier = Modifier.padding(20.dp),
            color = MaterialTheme.colors.onPrimary
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colors.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colors.onPrimary,
                focusedTextColor = MaterialTheme.colors.onPrimary,
                unfocusedTextColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                focusedContainerColor = MaterialTheme.colors.primary,
                unfocusedContainerColor = MaterialTheme.colors.primary,
            ),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Name",
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

        /*  OutlinedTextField(
              value = lastName,
              onValueChange = { lastName = it },
              colors = TextFieldDefaults.colors(
                  focusedIndicatorColor = MaterialTheme.colors.onPrimary,
                  unfocusedIndicatorColor = MaterialTheme.colors.onPrimary,
                  focusedTextColor = MaterialTheme.colors.onPrimary,
                  unfocusedTextColor = MaterialTheme.colors.onPrimary,
                  cursorColor = MaterialTheme.colors.onPrimary,
                  focusedContainerColor = MaterialTheme.colors.primary,
                  unfocusedContainerColor = MaterialTheme.colors.primary,
              ),
              modifier = Modifier
                  .padding(10.dp)
                  .fillMaxWidth(),
              label = {
                  Text(
                      text = "Last name",
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
  */
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

        OutlinedTextField(
            value = repeatedPassword,
            onValueChange = { repeatedPassword = it },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colors.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colors.onPrimary,
                focusedTextColor = MaterialTheme.colors.onPrimary,
                unfocusedTextColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                focusedContainerColor = MaterialTheme.colors.primary,
                unfocusedContainerColor = MaterialTheme.colors.primary,
            ),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Repeat Password",
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

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.onPrimary
                ),
            )
        }
        Text(
            text = "I accept all terms and conditions",
            color = MaterialTheme.colors.onPrimary,
            fontFamily = FontFamily.fontFamilyLight
        )


        Button(
            onClick = {
                var result = viewModel.authenticationSignUp(
                    name,
                    email,
                    password,
                    repeatedPassword,
                    isChecked
                )
                if (result == "Correct") {
                    viewModel.signUpUser(email, password)
                    viewModel.getResultFromSignUp.observe(activity) {
                        when (it) {
                            Constant.SUCCESS -> {
                                Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT)
                                    .show()
                                navHostController.popBackStack()
                            }
                            Constant.FAILURE -> {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
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
                text = "Sign Up",
                fontSize = 15.sp,
                color = MaterialTheme.colors.onSecondary,
                fontFamily = FontFamily.fontFamilyLight
            )
        }


    }

}

@Composable
fun successResultSignIn() = true

fun failureResultSigIn() = true

