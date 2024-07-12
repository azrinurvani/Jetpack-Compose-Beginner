package com.azrinurvani.jetpackcomposeforbeginner.ui.manifestlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azrinurvani.jetpackcomposeforbeginner.data.MarsRoverManifestRepo
import com.azrinurvani.jetpackcomposeforbeginner.di.IoDispatcher
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO 13 - Create ViewModel to handling transfer/carry data from data layer to presentation (UI) layer
@HiltViewModel
class MarsRoverManifestViewModel @Inject constructor(
    private val manifestRepo: MarsRoverManifestRepo,
    //TODO 89 - Add Params for ioDispatcher
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _roverManifestUiState : MutableStateFlow<RoverManifestUiState> =
        MutableStateFlow(RoverManifestUiState.Loading)
    val roverManifestUiState : StateFlow<RoverManifestUiState> get() = _roverManifestUiState

    fun getMarsRoverManifest(roverName: String){
        //TODO 90 - Implement ioDispatcher when launch viewModelScope
        viewModelScope.launch(ioDispatcher){
            _roverManifestUiState.value = RoverManifestUiState.Loading
            manifestRepo.getMarsRoverManifest(roverName = roverName).collect{
                _roverManifestUiState.value = it
            }
        }
    }
}