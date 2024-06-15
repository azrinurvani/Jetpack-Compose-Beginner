package com.azrinurvani.jetpackcomposeforbeginner.ui.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.azrinurvani.jetpackcomposeforbeginner.ui.photolist.MarsRoverPhotoViewModel

//TODO 22 - Create PhotoScreen
@Composable
fun PhotoScreen(
    //TODO 36 - Add parameter roverName, sol, and marsRoverPhotoViewModel
    roverName : String?,
    sol : String?,
    marsRoverPhotoViewModel: MarsRoverPhotoViewModel
){
    if (roverName != null && sol != null){
        LaunchedEffect(Unit) {
            //TODO 37 - observe viewModel
            marsRoverPhotoViewModel.getMarsRoverPhoto(roverName,sol)
        }
    }
    Text(text = "Photo Screen")
}

