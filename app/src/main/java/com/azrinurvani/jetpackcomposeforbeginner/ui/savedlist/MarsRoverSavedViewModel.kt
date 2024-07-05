package com.azrinurvani.jetpackcomposeforbeginner.ui.savedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azrinurvani.jetpackcomposeforbeginner.data.MarsRoverPhotoRepo
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO 63 - Create new class for MarsRoverSavedViewModel and inject MarsRoverPhotoRepo
@HiltViewModel
class MarsRoverSavedViewModel @Inject constructor(
    private val marsRoverPhotoRepo: MarsRoverPhotoRepo
) : ViewModel(){

    //TODO 64 - Create getter and setter for marsPhotoUiSavedState
    private val _marsPhotoUiSavedState : MutableStateFlow<RoverPhotoUiState> =
        MutableStateFlow(RoverPhotoUiState.Loading)

    val marsPhotoUiSavedState : StateFlow<RoverPhotoUiState>
        get() = _marsPhotoUiSavedState


    //TODO 65 - getAllSaved to launch get data RoverPhoto from Local Data (call from repo)
    fun getAllSaved(){
        viewModelScope.launch(Dispatchers.IO) {
            marsRoverPhotoRepo.getAllSaved().collect{
                _marsPhotoUiSavedState.value = it
            }
        }
    }

    //TODO 66 - copy and paste changeSaveStatus() method from MarsRoverPhotoViewModel
    fun changeSaveStatus(roverPhotoUiModel: RoverPhotoUiModel){
        viewModelScope.launch(Dispatchers.IO) {
            if (roverPhotoUiModel.isSaved) {
                marsRoverPhotoRepo.removePhoto(roverPhotoUiModel = roverPhotoUiModel)
            }else{
                marsRoverPhotoRepo.savePhoto(roverPhotoUiModel = roverPhotoUiModel)
            }
        }
    }
    
}