package com.azrinurvani.jetpackcomposeforbeginner.ui.photolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azrinurvani.jetpackcomposeforbeginner.data.MarsRoverPhotoRepo
import com.azrinurvani.jetpackcomposeforbeginner.di.IoDispatcher
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO 35 - Create MarsRoverPhotoViewModel with inject MarsRoverPhotoRepo
@HiltViewModel
class MarsRoverPhotoViewModel @Inject constructor(
    private val marsRoverPhotoRepo: MarsRoverPhotoRepo,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){

    //TODO 39 - Create live data for RoverPhotoUiState
    private val _roverPhotoUiState : MutableStateFlow<RoverPhotoUiState> = MutableStateFlow(RoverPhotoUiState.Loading)

    val roverPhotoUiState : StateFlow<RoverPhotoUiState> get() = _roverPhotoUiState

    fun getMarsRoverPhoto(roverName:String, sol : String) {
        viewModelScope.launch(ioDispatcher) {
            _roverPhotoUiState.value = RoverPhotoUiState.Loading
            marsRoverPhotoRepo.getMarsRoverPhoto(roverName,sol).collect{
                _roverPhotoUiState.value = it
            }
        }
    }

    //TODO 54 - Create new function to change status save
    //If data has been saved, the old data will be removed and if data not saved before, the data will be added
    fun changeSaveStatus(roverPhotoUiModel: RoverPhotoUiModel){
        viewModelScope.launch(ioDispatcher) { //Replace Dispatcher.IO with ioDispatcher variable
            if (roverPhotoUiModel.isSaved) {
                marsRoverPhotoRepo.removePhoto(roverPhotoUiModel = roverPhotoUiModel)
            }else{
                marsRoverPhotoRepo.savePhoto(roverPhotoUiModel = roverPhotoUiModel)
            }
        }
    }
}