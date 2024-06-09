package com.azrinurvani.jetpackcomposeforbeginner.ui.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.azrinurvani.jetpackcomposeforbeginner.ui.manifestlist.MarsRoverManifestViewModel

@Composable
fun ManifestScreen(
    roverName:String?,
    marsRoverManifestViewModel : MarsRoverManifestViewModel
){
    if (roverName != null){
        LaunchedEffect(Unit) { //LaunchEffect will be guarantee start only once
            marsRoverManifestViewModel.getMarsRoverManifest(roverName)
        }
    }
    Text(text = "Manifest Screen $roverName")
}

@Preview
@Composable
fun ManifestScreenPreview(){
//    ManifestScreen("Perseverance",)
}