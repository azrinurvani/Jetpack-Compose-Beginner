package com.azrinurvani.jetpackcomposeforbeginner.ui.photolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azrinurvani.jetpackcomposeforbeginner.data.MarsRoverPhotoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO 35 - Create MarsRoverPhotoViewModel with inject MarsRoverPhotoRepo
@HiltViewModel
class MarsRoverPhotoViewModel @Inject constructor(
    private val marsRoverPhotoRepo: MarsRoverPhotoRepo
) : ViewModel(){

    fun getMarsRoverPhoto(roverName:String, sol : String) {
        viewModelScope.launch {
            marsRoverPhotoRepo.getMarsRoverPhoto(roverName,sol).collect{

            }
        }
    }
}