package com.azrinurvani.jetpackcomposeforbeginner.ui.manifestlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azrinurvani.jetpackcomposeforbeginner.data.MarsRoverManifestRepo
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO 13 - Create ViewModel to handling transfer/carry data from data layer to presentation (UI) layer
@HiltViewModel
class MarsRoverManifestViewModel @Inject constructor(
    private val manifestRepo: MarsRoverManifestRepo
) : ViewModel() {

    init {
        Log.d(javaClass.name, "MarsRoverManifestViewModel is working...")
    }

    private val _roverManifestUiState : MutableStateFlow<RoverManifestUiState> =
        MutableStateFlow(RoverManifestUiState.Loading)
    val roverManifestUiState : StateFlow<RoverManifestUiState> get() = _roverManifestUiState

    fun getMarsRoverManifest(roverName: String){
        viewModelScope.launch{
            _roverManifestUiState.value = RoverManifestUiState.Loading
            manifestRepo.getMarsRoverManifest(roverName = roverName).collect{
                _roverManifestUiState.value = it
            }
        }
    }
}