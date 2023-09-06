package com.pzbdownloaders.scribble.login_and_signup_feature.sign_up

import android.view.animation.Transformation
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pzbdownloaders.scribble.common.domain.utils.Constant
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

        var passwordVisibilityToggle by remember {
            mutableStateOf(false)
        }

        var passwordRepeatedVisibilityToggle by remember {
            mutableStateOf(false)
        }

        var logInButtonWidth by remember {
            mutableStateOf(100)
        }

        var signUpButtonClick by remember {
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
            ),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily.fontFamilyRegular
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
            ),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily.fontFamilyRegular
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
            visualTransformation = if (!passwordVisibilityToggle) PasswordVisualTransformation() else VisualTransformation.None,
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
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily.fontFamilyRegular
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
            visualTransformation = if (!passwordRepeatedVisibilityToggle) PasswordVisualTransformation() else VisualTransformation.None,
            shape = MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(15.dp),
                topEnd = CornerSize(15.dp),
                bottomStart = CornerSize(15.dp),
                bottomEnd = CornerSize(15.dp),
            ),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily.fontFamilyRegular
            ),
            trailingIcon = {
                if (!passwordRepeatedVisibilityToggle)
                    IconButton(onClick = {
                        passwordRepeatedVisibilityToggle = !passwordRepeatedVisibilityToggle
                    }) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Visibility",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    } else {
                    IconButton(onClick = {
                        passwordRepeatedVisibilityToggle = !passwordRepeatedVisibilityToggle
                    }) {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = "Visibility",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
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
            fontFamily = FontFamily.fontFamilyRegular
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
                    signUpButtonClick = !signUpButtonClick
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
                .height(60.dp),
            /*   .border(
                   border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
                   MaterialTheme.shapes.medium.copy(
                       topStart = CornerSize(15.dp),
                       topEnd = CornerSize(15.dp),
                       bottomEnd = CornerSize(15.dp),
                       bottomStart = CornerSize(15.dp),
                   )
               )*/
            shape = MaterialTheme.shapes.medium.copy(
                topStart = CornerSize(15.dp),
                topEnd = CornerSize(15.dp),
                bottomStart = CornerSize(15.dp),
                bottomEnd = CornerSize(15.dp),
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.onPrimary
            ),
        ) {
            Text(
                text = if (!signUpButtonClick) "Sign Up" else "Signing up...",
                fontSize = 15.sp,
                color = MaterialTheme.colors.onSecondary,
                fontFamily = FontFamily.fontFamilyRegular
            )
            if (signUpButtonClick) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(20.dp)
                        .height(20.dp),
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }

}

