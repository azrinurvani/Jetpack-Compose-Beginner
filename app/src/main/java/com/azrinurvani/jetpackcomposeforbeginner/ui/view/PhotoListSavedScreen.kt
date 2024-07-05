package com.azrinurvani.jetpackcomposeforbeginner.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiState
import com.azrinurvani.jetpackcomposeforbeginner.ui.savedlist.MarsRoverSavedViewModel

//TODO 41 - Create PhotoListSavedScreen
@Composable
fun PhotoListSavedScreen(
    //TODO 67 - Add modifier and MarsRoverSavedViewModel to param PhotoListSavedScreen
    modifier: Modifier,
    marsRoverSavedViewModel : MarsRoverSavedViewModel
){
    //TODO 68 - Call marsPhotoUiSavedState live data
    val viewState by marsRoverSavedViewModel.marsPhotoUiSavedState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        marsRoverSavedViewModel.getAllSaved()
    }

    //TODO 69 - Add condition for viewState from Live Data
    when(val roverPhotoUiState = viewState){
        is RoverPhotoUiState.Loading -> Loading()

        is RoverPhotoUiState.Error -> Error()

        is RoverPhotoUiState.Success-> PhotoList(
            modifier = modifier,
            roverPhotoUiModelList = roverPhotoUiState.roverPhotoUiModelList
        ) { roverPhotoUiModel -> //OnClick
            marsRoverSavedViewModel.changeSaveStatus(roverPhotoUiModel)
        }

    }
}