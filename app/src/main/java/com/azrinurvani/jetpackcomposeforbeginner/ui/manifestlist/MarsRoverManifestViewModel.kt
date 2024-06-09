package com.azrinurvani.jetpackcomposeforbeginner.ui.manifestlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.azrinurvani.jetpackcomposeforbeginner.data.MarsRoverManifestRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarsRoverManifestViewModel @Inject constructor(
    private val manifestRepo: MarsRoverManifestRepo
) : ViewModel() {

    init {
        Log.d(javaClass.name, "MarsRoverManifestViewModel is working...")
    }

    fun getMarsRoverManifest(roverName: String){
        viewModelScope.launch{
            manifestRepo.getMarsRoverManifest(roverName = roverName).collect{

            }
        }
    }
}