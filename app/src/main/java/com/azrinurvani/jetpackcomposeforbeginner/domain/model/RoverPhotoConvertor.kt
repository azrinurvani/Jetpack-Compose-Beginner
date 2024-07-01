package com.azrinurvani.jetpackcomposeforbeginner.domain.model

import com.azrinurvani.jetpackcomposeforbeginner.db.MarsRoverSavedLocalModel

//TODO 51 - Create convertor for mapping data from network model to local model
fun toDbModel(roverPhotoUiModel: RoverPhotoUiModel) : MarsRoverSavedLocalModel{
    return MarsRoverSavedLocalModel(
        roverPhotoId = roverPhotoUiModel.id,
        roverName = roverPhotoUiModel.roverName,
        imgSrc = roverPhotoUiModel.imgSrc,
        sol = roverPhotoUiModel.sol,
        earthDate = roverPhotoUiModel.earthDate,
        cameraFullName = roverPhotoUiModel.cameraFullName
    )
}