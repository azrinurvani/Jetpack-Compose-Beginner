package com.azrinurvani.jetpackcomposeforbeginner.data

import com.azrinurvani.jetpackcomposeforbeginner.db.MarsRoverSavedPhotoDao
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiState
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.toDbModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.toUiModel
import com.azrinurvani.jetpackcomposeforbeginner.service.MarsRoverPhotoService
import com.azrinurvani.jetpackcomposeforbeginner.service.model.RoverPhotoRemoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//TODO 34 - Create MarsRoverPhotoRepo to call and emit function from service (Call API)
class MarsRoverPhotoRepo @Inject constructor(
    private val marsRoverPhotoService: MarsRoverPhotoService,
    //TODO 53 - add new params for MarsRoverSavedPhotoDao
    private val marsRoverSavedPhotoDao: MarsRoverSavedPhotoDao
) {

    private fun getAllRemoteMarsRoverPhotos(
        roverName: String,
        sol: String
    ) : Flow<RoverPhotoRemoteModel?> = flow {
        try {
            val networkResult = marsRoverPhotoService.getMarsRoverPhoto(
                roverName.lowercase(),
                sol
            )
            emit(networkResult)
        }catch (t : Throwable){
            emit(null)
        }
    }
    fun getMarsRoverPhoto(roverName : String, sol : String) : Flow<RoverPhotoUiState> =
        marsRoverSavedPhotoDao.allSavedIds(sol,roverName).combine(
            getAllRemoteMarsRoverPhotos(roverName,sol)
        ){ local, remote->
            remote?.let { roverPhotoRemoteModel ->
                RoverPhotoUiState.Success(
                    roverPhotoRemoteModel.photos.map { photo ->
                        RoverPhotoUiModel(
                            id = photo.id,
                            roverName = photo.rover.name,
                            imgSrc = photo.imgSrc,
                            sol = photo.sol.toString(),
                            earthDate = photo.earthDate,
                            cameraFullName = photo.camera.fullName,
                            isSaved = local.contains(photo.id)
                        )
                    }
                )
            } ?: run {
                RoverPhotoUiState.Error
            }
        }

//    fun getMarsRoverPhoto(roverName : String, sol : String) : Flow<RoverPhotoUiState> = flow {
//        try {
//            val networkResult = marsRoverPhotoService.getMarsRoverPhoto(roverName = roverName, sol = sol)
//            emit(
//                RoverPhotoUiState.Success(
//                    networkResult.photos.map { photo ->
//                        RoverPhotoUiModel(
//                            id = photo.id,
//                            roverName = photo.rover.name,
//                            imgSrc = photo.imgSrc,
//                            sol = photo.sol.toString(),
//                            earthDate = photo.earthDate,
//                            cameraFullName = photo.camera.fullName
//                        )
//                    }
//                )
//            )
//        }catch (t: Throwable){
//            emit(RoverPhotoUiState.Error)
//        }
//    }

    suspend fun savePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        marsRoverSavedPhotoDao.insert(toDbModel(roverPhotoUiModel))
    }

    suspend fun removePhoto(roverPhotoUiModel: RoverPhotoUiModel){
        marsRoverSavedPhotoDao.delete(toDbModel(roverPhotoUiModel))
    }

    //TODO 62 - Create function getAllSaved in MarsRoverPhoto and call getAllSaved from MarsRoverSavedPhotoDao
    fun getAllSaved() : Flow<RoverPhotoUiState> =
        marsRoverSavedPhotoDao.getAllSaved().map { localModel->
            RoverPhotoUiState.Success(toUiModel(localModel))
        }

}