package com.azrinurvani.jetpackcomposeforbeginner.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiState
import com.azrinurvani.jetpackcomposeforbeginner.ui.manifestlist.MarsRoverManifestViewModel

@Composable
fun ManifestScreen(
    roverName:String?,
    marsRoverManifestViewModel : MarsRoverManifestViewModel
){
    val viewState by marsRoverManifestViewModel.roverManifestUiState.collectAsStateWithLifecycle()

    if (roverName != null){
        LaunchedEffect(Unit) { //LaunchEffect will be guarantee start only once
            marsRoverManifestViewModel.getMarsRoverManifest(roverName)
        }
        when(val roverManifestUiState = viewState){
          RoverManifestUiState.Error -> Error()
          RoverManifestUiState.Loading -> Loading()
          is RoverManifestUiState.Success -> ManifestList(
              roverManifestUiModelList = roverManifestUiState.roverManifestUiModelList
          )
        }
    }

}



//Unused
//@Preview
//@Composable
//fun ManifestScreenPreview(){
////    ManifestScreen("Perseverance",)
//}