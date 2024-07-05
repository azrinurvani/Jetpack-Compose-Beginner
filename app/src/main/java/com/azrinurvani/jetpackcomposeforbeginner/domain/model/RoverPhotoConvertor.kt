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

//TODO 60 - Create model convertor or mapping from MarsRoverSavedLocalModel to RoverPhotoUiModel
fun toUiModel(marsRoverSavedLocalModelList: List<MarsRoverSavedLocalModel>) =
    marsRoverSavedLocalModelList.map { photo->
        RoverPhotoUiModel(
            id = photo.roverPhotoId,
            roverName = photo.roverName,
            imgSrc = photo.imgSrc,
            sol = photo.sol,
            earthDate = photo.earthDate,
            cameraFullName = photo.cameraFullName,
            isSaved = true
        )
    }