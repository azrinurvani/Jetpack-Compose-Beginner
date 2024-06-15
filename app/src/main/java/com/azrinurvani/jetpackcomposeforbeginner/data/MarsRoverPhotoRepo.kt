package com.azrinurvani.jetpackcomposeforbeginner.data

import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiState
import com.azrinurvani.jetpackcomposeforbeginner.service.MarsRoverPhotoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//TODO 34 - Create MarsRoverPhotoRepo to call and emit function from service (Call API)
class MarsRoverPhotoRepo @Inject constructor(
    private val marsRoverPhotoService: MarsRoverPhotoService
) {
    fun getMarsRoverPhoto(roverName : String, sol : String) : Flow<RoverPhotoUiState> = flow {
        try {
            val networkResult = marsRoverPhotoService.getMarsRoverPhoto(roverName = roverName, sol = sol)
            emit(
                RoverPhotoUiState.Success(
                    networkResult.photos.map { photo ->
                        RoverPhotoUiModel(
                            id = photo.id,
                            roverName = photo.rover.name,
                            imgSrc = photo.imgSrc,
                            sol = photo.sol.toString(),
                            earthDate = photo.earthDate,
                            cameraFullName = photo.camera.fullName
                        )
                    }
                )
            )
        }catch (t: Throwable){
            emit(RoverPhotoUiState.Error)
        }
    }

}