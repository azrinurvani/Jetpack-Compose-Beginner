package com.azrinurvani.jetpackcomposeforbeginner.domain.model

//TODO 32 -  Create RoverPhotoUiState
sealed class RoverPhotoUiState{
    data class Success(
        val roverPhotoUiModelList : List<RoverPhotoUiModel>
    ) : RoverPhotoUiState()
    object Loading : RoverPhotoUiState()
    object Error : RoverPhotoUiState()
}

data class RoverPhotoUiModel(
    val id : Int,
    val roverName : String,
    val imgSrc : String,
    val sol : String,
    val earthDate : String,
    val cameraFullName : String,
    //TODO 52 - Add field isSaved for condition data has been saved or not
    val isSaved : Boolean
)