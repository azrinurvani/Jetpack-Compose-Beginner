package com.azrinurvani.jetpackcomposeforbeginner.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiState
import com.azrinurvani.jetpackcomposeforbeginner.ui.photolist.MarsRoverPhotoViewModel

//TODO 22 - Create PhotoScreen
@Composable
fun PhotoScreen(
    //TODO 36 - Add parameter roverName, sol, and marsRoverPhotoViewModel
    roverName : String?,
    sol : String?,
    marsRoverPhotoViewModel: MarsRoverPhotoViewModel
){

    val viewState by marsRoverPhotoViewModel.roverPhotoUiState.collectAsStateWithLifecycle()

    if (roverName != null && sol != null){
        LaunchedEffect(Unit) {
            //TODO 37 - observe viewModel
            marsRoverPhotoViewModel.getMarsRoverPhoto(roverName,sol)
        }
        //TODO 40 - Implement RoverPhotoUiState when observe live data
        when(val roverPhotoUiState = viewState){
            is RoverPhotoUiState.Loading ->{
                Loading()
            }
            is RoverPhotoUiState.Error ->{
                Error()
            }
            is RoverPhotoUiState.Success ->{
                PhotoList(roverPhotoUiModelList = roverPhotoUiState.roverPhotoUiModelList)
            }
        }
    }
//    Text(text = "Photo Screen")
}

