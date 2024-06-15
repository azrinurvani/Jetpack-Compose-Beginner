package com.azrinurvani.jetpackcomposeforbeginner.data

import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiState
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.toUiModel
import com.azrinurvani.jetpackcomposeforbeginner.service.MarsRoverManifestService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

//TODO 12 - Create Repository to call function from network service (API)
class MarsRoverManifestRepo @Inject constructor(
    private val marsRoverManifestService: MarsRoverManifestService
){

    fun getMarsRoverManifest(roverName : String) : Flow<RoverManifestUiState> = flow {
        try {
            emit(toUiModel(
                marsRoverManifestService.getMarsRoverManifest(roverName)
            ))
        }catch (t : Throwable){
            emit(RoverManifestUiState.Error)
        }
    }
}