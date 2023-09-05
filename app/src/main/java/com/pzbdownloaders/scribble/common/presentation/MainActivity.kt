package com.pzbdownloaders.scribble.common.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.pzbdownloaders.scribble.ui.theme.ScribbleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var viewModel: MainActivityViewModel
    lateinit var result: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        val sharedPreferences = getSharedPreferences("rememberUser", Context.MODE_PRIVATE)
        result = sharedPreferences.getString("LoggedInUser", "nothing")!!
        Log.i("logged",result!!)
        setContent {
            ScribbleTheme {
                // A surface container using the 'background' color from the theme
                var selectedIItem = remember{
                    mutableStateOf(0)
                }
                var selectedNote = remember{
                    mutableStateOf(0)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary)
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, viewModel, this@MainActivity, result, selectedIItem, selectedNote)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScribbleTheme {
    }
}