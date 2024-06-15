package com.azrinurvani.jetpackcomposeforbeginner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint

//TODO 21 - Create MainActivity extends ComponentActivity with annotation @AndroidEntryPoint
// and implement NavCompose() in setContent
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            JetpackComposeForBeginnerTheme {
//               RoverList()
//            }
            NavCompose()
        }
    }
}
