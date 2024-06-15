package com.azrinurvani.jetpackcomposeforbeginner.domain.model

import com.azrinurvani.jetpackcomposeforbeginner.service.model.RoverManifestRemoteModel

//TODO 10 - Create function to Convert/Mapping model from response to domain layer
fun toUiModel(roverManifestRemoteModel: RoverManifestRemoteModel) : RoverManifestUiState =
    RoverManifestUiState.Success(roverManifestRemoteModel.photoManifest.photos.map { photo->
        RoverManifestUiModel(
            sol = photo.sol.toString(),
            earthDate = photo.earthDate,
            photoNumber = photo.totalPhotos.toString()
        )
    }.sorted())